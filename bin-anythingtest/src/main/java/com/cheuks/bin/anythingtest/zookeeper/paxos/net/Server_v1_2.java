package com.cheuks.bin.anythingtest.zookeeper.paxos.net;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.cheuks.bin.anythingtest.zookeeper.paxos.net.mananger.AbstractMananger;
import com.cheuks.bin.anythingtest.zookeeper.paxos.net.mananger.AcceptMananger;
import com.cheuks.bin.anythingtest.zookeeper.paxos.net.mananger.ReaderMananger;
import com.cheuks.bin.anythingtest.zookeeper.paxos.net.mananger.ReleaseMananger;
import com.cheuks.bin.anythingtest.zookeeper.paxos.net.mananger.ScorterMananger;
import com.cheuks.bin.anythingtest.zookeeper.paxos.net.mananger.SelectorMananger;
import com.cheuks.bin.anythingtest.zookeeper.paxos.net.mananger.WritererMananger;

public class Server_v1_2 extends AbstractMananger {

	public static void main(String[] args) {
		Logger.getDefault().setErrorWrite(false);
		ExecutorService mananger = Executors.newFixedThreadPool(10);
		mananger.execute(new SelectorMananger(10086, 10087, 10088, 10089));
		mananger.execute(new ReleaseMananger(20000));
		mananger.execute(new ScorterMananger());
		mananger.execute(new AcceptMananger());
		mananger.execute(new ReaderMananger());
		mananger.execute(new WritererMananger());
	}

}
