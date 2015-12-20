package com.cheuks.bin.anythingtest.zookeeper.paxos.net.mananger;

import java.nio.channels.SelectionKey;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;

import com.cheuks.bin.anythingtest.zookeeper.paxos.net.Logger;

/***
 * *
 * 
 * Copyright 2015 CHEUK.BIN.LI Individual All
 * 
 * ALL RIGHT RESERVED
 * 
 * CREATE ON 2015年12月17日下午1:27:13
 * 
 * EMAIL:20796698@QQ.COM
 * 
 * GITHUB:https://github.com/fdisk123
 * 
 * @author CHEUK.BIN.LI
 * 
 * @see 分拣
 *
 */
public class ScorterMananger extends AbstractMananger {

	@Override
	public void run() {
		// System.err.println("分拣器启动");
		while (!Thread.interrupted()) {
			try {
				if (null != (key = ScorterQueue.poll(pollWait, TimeUnit.MICROSECONDS))) {
					if (key.isValid() && null != key.channel()) {
						/*
						 * if (key.isAcceptable()) { AcceptQueue.offer(key); }
						 * else
						 */if (key.isReadable()) {
							doTry(ReadDo, ReaderQueue, key);
						} else if (key.isWritable()) {
							doTry(WriteDo, WriterQueue, key);
						}
						// else if (key.isConnectable()) {}
						// try {
						// System.err.println("分拣：" + new
						// Release(key).getMsg().getId() + (key.isWritable() ?
						// ":写" : key.isReadable() ? "读" : "x") + " " +
						// key.interestOps());
						// } catch (IOException e) {
						// e.printStackTrace();
						// }
					} else {
						key.cancel();
					}
				}
			} catch (InterruptedException e) {
				Logger.getDefault().error(this.getClass(), e);
			}
		}
	}

}
