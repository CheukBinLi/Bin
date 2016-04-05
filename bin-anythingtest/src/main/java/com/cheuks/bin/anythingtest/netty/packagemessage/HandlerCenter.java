package com.cheuks.bin.anythingtest.netty.packagemessage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import com.cheuks.bin.anythingtest.netty.packagemessage.MsgBuf.MsgBody;

import io.netty.channel.ChannelHandlerContext;

public class HandlerCenter {

	public static BlockingQueue<Point<ChannelHandlerContext>> queue = new LinkedBlockingDeque<HandlerCenter.Point<ChannelHandlerContext>>();
	private static volatile boolean interrupt = false;
	private final int processCount = Runtime.getRuntime().availableProcessors();

	static class worker implements Runnable {

		public void run() {
			while (!interrupt) {
				
			}
		}
	}

	static class Point<T> {
		private MsgBody msg;
		private T channelHandlerContext;

		public MsgBody getMsg() {
			return msg;
		}

		public Point setMsg(MsgBody msg) {
			this.msg = msg;
			return this;
		}

		public T getChannelHandlerContext() {
			return channelHandlerContext;
		}

		public Point setChannelHandlerContext(T channelHandlerContext) {
			this.channelHandlerContext = channelHandlerContext;
			return this;
		}

	}
}
