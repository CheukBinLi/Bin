package com.cheuks.bin.anythingtest.zookeeper.paxos;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/***
 * *
 * 
 * Copyright 2015    CHEUK.BIN.LI Individual All
 *  
 * ALL RIGHT RESERVED
 *  
 * CREATE ON 2015年12月16日下午4:38:31
 *  
 * EMAIL:20796698@QQ.COM
 *  
 * GITHUB:https://github.com/fdisk123
 * 
 * @author CHEUK.BIN.LI
 * 
 * @see 提案历史
 *
 */
public class ProposalHistory {

	private LinkedList<Integer> history = new LinkedList<Integer>();
	private Set<Integer> voteQueue = new HashSet<Integer>();
	private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

	/**
	 * 是否可以投票
	 * @param bill
	 * @return
	 */
	public boolean canVote(int bill) {
		try {
			if (lock.writeLock().tryLock())
				if (bill > history.getLast() || voteQueue.contains(bill)) {
					voteQueue.add(bill);
					return true;
				}
		} finally {
			lock.writeLock().unlock();
		}
		return false;
	}

}
