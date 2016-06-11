package com.cheuks.bin.anythingtest.zookeeper.paxos.net;

import com.cheuks.bin.anythingtest.zookeeper.paxos.net.mananger.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server_v1_2 extends AbstractMananger {

	public static void main(String[] args) {
		Logger.getDefault().setErrorWrite(false).setInfoWrite(true);
		ExecutorService mananger = Executors.newFixedThreadPool(10);
		mananger.execute(new SelectorMananger(10086, 10087, 10088, 10089));
		mananger.execute(new ReleaseMananger(5000, 8000));
		// mananger.execute(new ScorterMananger());
		// mananger.execute(new AcceptMananger());
		mananger.execute(new ReaderMananger());
		mananger.execute(new WritererMananger());
	}

}
