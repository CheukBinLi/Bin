package com.cheuks.bin.anythingtest.zookeeper.paxos.net.mananger;

import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;

import com.cheuks.bin.anythingtest.zookeeper.paxos.net.ConnectionMsg;
import com.cheuks.bin.anythingtest.zookeeper.paxos.net.Logger;

public class AcceptMananger extends AbstractMananger {

	public AcceptMananger() {
		super();
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			if (null != (key = AcceptQueue.poll())) {
				msg = (ConnectionMsg) key.attachment();
				try {
					channel = ((ServerSocketChannel) key.channel()).accept();
					channel.configureBlocking(false);
					channel.finishConnect();
					key = channel.register(key.selector(), SelectionKey.OP_READ, msg);
					// key.
				} catch (Exception e) {
					Logger.getDefault().error(this.getClass(), e);
				} finally {
					msg.updateConnectionTime().enableSelectable();
				}
			}
		}
	}

}
