package com.cheuks.bin.anythingtest.zookeeper.paxos;

public abstract class Role {

	protected String roleType;
	protected ProposalHistory history;

	/***
	 * 投票
	 * @return
	 */
	public abstract boolean vote();

}
