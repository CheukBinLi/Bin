package com.cheuks.bin.anythingtest.zookeeper.paxos.net;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger implements Log {

	static Logger newInstance = null;
	static final SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy_MM_dd");
	RandomAccessFile raf;
	/***
	 * 日期 [info] 
	 */
	String defaultFormat = "%s [%s] %s : %s \r\n";

	public static Logger getDefault() {
		if (null == newInstance)
			return newInstance = new Logger();
		return newInstance;
	}

	public static Logger getDefault(String path) {
		return newInstance = new Logger(path);
	}

	public Logger() {
		super();
		try {
			raf = new RandomAccessFile(new File(this.getClass().getResource("/").getFile() + dataFormat.format(new Date()) + "_log.txt"), "rws");
			//			System.out.println(raf.length());
			//			if (raf.length() < 10240000)
			//				raf.seek(raf.length());
			//			else
			//				raf.setLength(0);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Logger(String path) {
		super();
		try {
			raf = new RandomAccessFile(path + dataFormat.format(new Date()) + "_log.txt", "rws");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Logger log(String msg, boolean write, int level) {
		if (level > 0)
			System.err.println(msg);
		else
			System.out.println(msg);
		if (write)
			try {
				write(msg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		return this;
	}

	public Logger info(Class<?> clazz, String msg) {
		return log(String.format(defaultFormat, new Date(), "INFO", clazz.getName(), msg), true, 0);
	}

	public Logger error(Class<?> clazz, Throwable msg) {
		return log(String.format(defaultFormat, new Date(), "EXCEPTION", clazz.getName(), msg), true, 1);
	}

	private Logger write(String msg) throws IOException {
		raf.write(msg.getBytes());
		return this;
	}

	public static void main(String[] args) throws IOException {
		new Logger().info(Logger.class, "小明明小小明").error(Logger.class, new Throwable("小明明小小明"));
	}

}
