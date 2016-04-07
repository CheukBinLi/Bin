package com.cheuks.bin.anythingtest.netty.packagemessage;

import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

import com.cheuks.bin.anythingtest.netty.BaseClient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

public abstract class ChannelPoolService extends BaseClient {

	private final BlockingDeque<Channel> pool = new LinkedBlockingDeque<Channel>();

	private final Map<Channel, Heartbeat> heartbeat = new ConcurrentHashMap<Channel, Heartbeat>();

	private static ChannelPoolService singleInstance = null;

	private int max = 100;

	private int keepLine = 5;

	private long heartbeatInterval = 5000;

	private volatile boolean interrupt = false;

	private final AtomicInteger current = new AtomicInteger();

	private InetSocketAddress address;

	private volatile boolean isAlive;

	private Object heartbeatPackage;

	private final CyclicBarrier lock = new CyclicBarrier(3);

	private ExecutorService executorService = Executors.newFixedThreadPool(this.max + 10);

	public void start() {
		if (isAlive)
			return;
		executorService.submit(new Dispatcher(10000));
		executorService.submit(new LineChecker(10000 >> 1));
		isAlive = true;

	}

	public void stop() {
		this.interrupt = true;
		try {
			lock.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}
		this.interrupt = false;
		isAlive = false;
		lock.reset();
	}

	public synchronized Channel pop() throws InterruptedException {
		Channel channel = pool.pop();
		try {
			if (channel.isActive())
				return channel;
			channel = connection(this.address);
			return channel;
		} finally {
			current.addAndGet(1);
		}
	}

	public synchronized void release(Channel channel) {
		pool.offerLast(channel);
		current.addAndGet(-1);
	}

	/***
	 * *
	 * 
	 * Copyright 2015    CHEUK.BIN.LI Individual All
	 *  
	 * ALL RIGHT RESERVED
	 *  
	 * CREATE ON 2016年4月7日下午3:35:27
	 *  
	 * EMAIL:20796698@QQ.COM
	 *  
	 * GITHUB:https://github.com/fdisk123
	 * 
	 * @author CHEUK.BIN.LI
	 * 
	 * @see  调度器
	 *
	 */
	class Dispatcher implements Runnable {
		long interval = 20000;

		public void run() {
			while (!interrupt) {
				try {
					Thread.sleep(interval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (pool.size() < 1 && current.get() < max) {
					try {
						for (int i = 0; i < 5; i++)
							pool.offerLast(connection(address));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				else if (pool.size() > keepLine) {
					for (; pool.size() > keepLine;)
						pool.removeLast().close();
				}
			}
			try {
				lock.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
		}

		public Dispatcher(long interval) {
			super();
			this.interval = interval;
		}
	}

	/***
	 * *
	 * 
	 * Copyright 2015    CHEUK.BIN.LI Individual All
	 *  
	 * ALL RIGHT RESERVED
	 *  
	 * CREATE ON 2016年4月7日下午5:09:39
	 *  
	 * EMAIL:20796698@QQ.COM
	 *  
	 * GITHUB:https://github.com/fdisk123
	 * 
	 * @author CHEUK.BIN.LI
	 * 
	 * @see  线路状态管理
	 *
	 */
	class LineChecker implements Runnable {
		long interval = 10000;

		public void run() {
			Iterator<Channel> it;
			Channel channel;
			while (!interrupt) {
				try {
					Thread.sleep(interval);
					it = pool.iterator();
					while (it.hasNext()) {
						if (!(channel = it.next()).isActive()) {
							heartbeat.remove(channel).stop();
							pool.remove(channel);
							//							System.out.println("移除断线！");
						}
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			try {
				lock.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (BrokenBarrierException e) {
				e.printStackTrace();
			}
		}

		public LineChecker(long interval) {
			super();
			this.interval = interval;
		}
	}

	/***
	 * *
	 * 
	 * Copyright 2015    CHEUK.BIN.LI Individual All
	 *  
	 * ALL RIGHT RESERVED
	 *  
	 * CREATE ON 2016年4月7日下午5:08:54
	 *  
	 * EMAIL:20796698@QQ.COM
	 *  
	 * GITHUB:https://github.com/fdisk123
	 * 
	 * @author CHEUK.BIN.LI
	 * 
	 * @see 心跳线程
	 *
	 */
	class Heartbeat implements Runnable {
		long interval;
		Channel channel;
		Object heatbatePackage;
		private volatile boolean interrupt = false;

		public void run() {
			try {
				while (!interrupt) {
					System.err.println("心跳");
					Thread.sleep(interval);
					channel.writeAndFlush(heatbatePackage);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		public void stop() {
			interrupt = true;
		}

		public Heartbeat(long interval, Channel channel, Object heatbatePackage) {
			super();
			this.interval = interval;
			this.channel = channel;
			this.heatbatePackage = heatbatePackage;
		}
	}

	public int getMax() {
		return max;
	}

	public ChannelPoolService setMax(int max) {
		this.max = max;
		return this;
	}

	public int getKeepLine() {
		return keepLine;
	}

	public ChannelPoolService setKeepLine(int keepLine) {
		this.keepLine = keepLine;
		return this;
	}

	public ChannelPoolService(InetSocketAddress address) {
		this(address, false, 0, null);
	}

	public ChannelPoolService(InetSocketAddress address, long heartbeatInterval, Object heartbeatPackage) {
		this(address, false, heartbeatInterval, heartbeatPackage);
	}

	@Override
	public Channel connection(InetSocketAddress address) throws InterruptedException {
		Channel channel = super.connection(address);
		if (null != heartbeatPackage) {
			Heartbeat h = new Heartbeat(this.heartbeatInterval, channel, this.heartbeatPackage);
			executorService.execute(h);
			heartbeat.put(channel, h);
		}
		return channel;
	}

	public ChannelPoolService(InetSocketAddress address, boolean setSingle) {
		this(address, setSingle, 0, null);
	}

	public ChannelPoolService(InetSocketAddress address, boolean setSingle, long heartbeatInterval, Object heartbeatPackage) {
		this.address = address;
		this.heartbeatInterval = heartbeatInterval;
		this.heartbeatPackage = heartbeatPackage;
		for (; pool.size() < 5;)
			try {
				pool.offerLast(connection(address));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		if (setSingle)
			singleInstance = this;
	}

	public ChannelPoolService(int max, int keepLine, InetSocketAddress address, long heartbeatInterval, Object heartbeatPackage) {
		this(address, heartbeatInterval, heartbeatPackage);
		this.max = max;
		this.keepLine = keepLine;
		executorService = Executors.newFixedThreadPool(this.max + 10);
	}

	public static ChannelPoolService getSingleinstance() {
		return singleInstance;
	}

	public static ChannelPoolService setSingleinstance(ChannelPoolService channelPoolService) {
		return (singleInstance = channelPoolService);
	}

	public static void main(String[] args) throws InterruptedException {
		InetSocketAddress address = new InetSocketAddress("127.0.0.1", 1190);
		ChannelPoolService cps = new ChannelPoolService(address, 5000, null) {
			@Override
			public Bootstrap setting(Bootstrap client) {
				return client.handler(new ChannelInitializer<SocketChannel>() {

					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(new MessageCodec());
						ch.pipeline().addLast(new ClientHandler());
					}
				});
			}
		};
		cps.start();
		Thread.sleep(5000);
		cps.stop();
		System.out.println("结束");
	}
}
