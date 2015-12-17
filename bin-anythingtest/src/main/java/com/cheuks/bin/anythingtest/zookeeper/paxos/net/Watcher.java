package com.cheuks.bin.anythingtest.zookeeper.paxos.net;

import java.io.Serializable;

public interface Watcher extends Serializable {

	public void process(WatchEven even);

}
