package com.cheuks.bin.anythingtest.netty.packagemessage;

import com.cheuks.bin.anythingtest.netty.packagemessage.MsgBuf.MsgBody;
import com.google.protobuf.ByteString;
import io.netty.channel.ChannelHandlerContext;

import java.lang.reflect.Method;
import java.util.concurrent.*;

public class HandlerCenter {

	private static final HandlerCenter newInstance = new HandlerCenter();
	public static BlockingQueue<people<ChannelHandlerContext>> queue = new LinkedBlockingDeque<HandlerCenter.people<ChannelHandlerContext>>();
	private static volatile boolean interrupt = false;
	private final int processCount = Runtime.getRuntime().availableProcessors();
	private ExecutorService executorService = null;

	private HandlerCenter() {
	}

	/***
	 * 添加任务
	 * @param people
	 */
	public static final void join(people<ChannelHandlerContext> people) {
		queue.offer(people);
	}

	public static final HandlerCenter newInstance() {
		return newInstance;
	}

	/***
	 * 开始工作
	 */
	public void start() {
		if (null == executorService || interrupt == true) {
			executorService = Executors.newFixedThreadPool(processCount << 1);
			for (int i = 0, b = processCount << 1; i < b; i++) {
				executorService.submit(new worker());
			}
		}
	}

	/***
	 * 结束工作
	 */
	public void stop() {
		interrupt = true;
		executorService.shutdown();
	}

	static class worker implements Runnable {

		ObjectCodec objectCoded = new DefaultObjectCoded();

		public void run() {
			people<ChannelHandlerContext> people;
			while (!interrupt) {
				try {
					people = queue.poll(10, TimeUnit.MILLISECONDS);
					if (null == people)
						continue;
					System.out.println("ooooooooooooooooooooo");
					MsgBody.Builder builder = MsgBody.newBuilder();
					Object result = null;
					Throwable exception = null;
					ClassInfo<?> classInfo = DefaultCachePool.newInstance().getObject(people.getMsg().getUid() + ":" + people.getMsg().getVersion());
					Method method = classInfo.getMethod(people.getMsg().getMethod());

					try {
						Object[] o = objectCoded.encodeByByte(people.getMsg().getParams().toByteArray());
						if (null == o)
							result = method.invoke(classInfo.getObj());
						else
							result = method.invoke(classInfo.getObj(), o);
					} catch (Throwable e) {
						e.printStackTrace();
						exception = e;
					}
					//结果
					if (null != exception) {
						result = exception;
					}
					System.out.println(result.toString());
					builder.setUid(people.getMsg().getUid()).setVersion(people.getMsg().getVersion()).setMethod(people.getMsg().getMethod());
					builder.setResult(ByteString.copyFrom(objectCoded.codeByByte(result)));
					builder.setException(null == exception);
					people.getChannelHandlerContext().writeAndFlush(new MessagePackage<MsgBody>(ServiceType.SERVICE_TYPE_SERVICE, builder.build()));
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (Throwable e) {
					e.printStackTrace();
				}

			}
		}
	}

	public static class people<T> {
		private MsgBody msg;
		private T channelHandlerContext;

		public MsgBody getMsg() {
			return msg;
		}

		public people<T> setMsg(MsgBody msg) {
			this.msg = msg;
			return this;
		}

		public T getChannelHandlerContext() {
			return channelHandlerContext;
		}

		public people<T> setChannelHandlerContext(T channelHandlerContext) {
			this.channelHandlerContext = channelHandlerContext;
			return this;
		}

		public people(MsgBody msg, T channelHandlerContext) {
			super();
			this.msg = msg;
			this.channelHandlerContext = channelHandlerContext;
		}

		public people() {
			super();
		}

	}

	public static void main(String[] args) {
	}
}
