package com.cheuks.bin.anythingtest.zookeeper_code.nio;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import org.apache.zookeeper.server.NIOServerCnxn;
import org.apache.zookeeper.server.NIOServerCnxnFactory;

class SelectorThread extends AbstractSelectThread {
	public boolean addAcceptedConnection(SocketChannel accepted) {
		if (stopped || !acceptedQueue.offer(accepted)) {
			return false;
		}
		wakeupSelector();
		return true;
	}

	/**
	 * Place interest op update requests onto a queue so that only the
	 * selector thread modifies interest ops, because interest ops
	 * reads/sets are potentially blocking operations if other select
	 * operations are happening.
	 */
	public boolean addInterestOpsUpdateRequest(SelectionKey sk) {
		if (stopped || !updateQueue.offer(sk)) {
			return false;
		}
		wakeupSelector();
		return true;
	}

	/**
	 * The main loop for the thread selects() on the connections and
	 * dispatches ready I/O work requests, then registers all pending
	 * newly accepted connections and updates any interest ops on the
	 * queue.
	 */
	public void run() {
		try {
			while (!stopped) {
				try {
					select();
					processAcceptedConnections();
					processInterestOpsUpdateRequests();
				} catch (RuntimeException e) {
					LOG.warn("Ignoring unexpected runtime exception", e);
				} catch (Exception e) {
					LOG.warn("Ignoring unexpected exception", e);
				}
			}

			// Close connections still pending on the selector. Any others
			// with in-flight work, let drain out of the work queue.
			for (SelectionKey key : selector.keys()) {
				NIOServerCnxn cnxn = (NIOServerCnxn) key.attachment();
				if (cnxn.isSelectable()) {
					cnxn.close();
				}
				cleanupSelectionKey(key);
			}
			SocketChannel accepted;
			while ((accepted = acceptedQueue.poll()) != null) {
				fastCloseSock(accepted);
			}
			updateQueue.clear();
		} finally {
			closeSelector();
			// This will wake up the accept thread and the other selector
			// threads, and tell the worker thread pool to begin shutdown.
			NIOServerCnxnFactory.this.stop();
			LOG.info("selector thread exitted run method");
		}
	}

	private void select() {
		try {
			selector.select();

			Set<SelectionKey> selected = selector.selectedKeys();
			ArrayList<SelectionKey> selectedList = new ArrayList<SelectionKey>(selected);
			Collections.shuffle(selectedList);
			Iterator<SelectionKey> selectedKeys = selectedList.iterator();
			while (!stopped && selectedKeys.hasNext()) {
				SelectionKey key = selectedKeys.next();
				selected.remove(key);

				if (!key.isValid()) {
					cleanupSelectionKey(key);
					continue;
				}
				if (key.isReadable() || key.isWritable()) {
					handleIO(key);
				}
				else {
					LOG.warn("Unexpected ops in select " + key.readyOps());
				}
			}
		} catch (IOException e) {
			LOG.warn("Ignoring IOException while selecting", e);
		}
	}

	/**
	 * Schedule I/O for processing on the connection associated with
	 * the given SelectionKey. If a worker thread pool is not being used,
	 * I/O is run directly by this thread.
	 */
	private void handleIO(SelectionKey key) {
		IOWorkRequest workRequest = new IOWorkRequest(this, key);
		NIOServerCnxn cnxn = (NIOServerCnxn) key.attachment();

		// Stop selecting this key while processing on its
		// connection
		cnxn.disableSelectable();
		key.interestOps(0);
		touchCnxn(cnxn);
		workerPool.schedule(workRequest);
	}

	/**
	 * Iterate over the queue of accepted connections that have been
	 * assigned to this thread but not yet placed on the selector.
	 */
	private void processAcceptedConnections() {
		SocketChannel accepted;
		while (!stopped && (accepted = acceptedQueue.poll()) != null) {
			SelectionKey key = null;
			try {
				key = accepted.register(selector, SelectionKey.OP_READ);
				NIOServerCnxn cnxn = createConnection(accepted, key, this);
				key.attach(cnxn);
				addCnxn(cnxn);
			} catch (IOException e) {
				// register, createConnection
				cleanupSelectionKey(key);
				fastCloseSock(accepted);
			}
		}
	}

	/**
	 * Iterate over the queue of connections ready to resume selection,
	 * and restore their interest ops selection mask.
	 */
	private void processInterestOpsUpdateRequests() {
		SelectionKey key;
		while (!stopped && (key = updateQueue.poll()) != null) {
			if (!key.isValid()) {
				cleanupSelectionKey(key);
			}
			NIOServerCnxn cnxn = (NIOServerCnxn) key.attachment();
			if (cnxn.isSelectable()) {
				key.interestOps(cnxn.getInterestOps());
			}
		}
	}
}