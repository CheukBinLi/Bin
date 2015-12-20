package com.cheuks.bin.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger implements Log {

	private static Logger newInstance = null;
	static final SimpleDateFormat dataFormat = new SimpleDateFormat("yyyy_MM_dd");
	boolean infoWrite = false, errorWite;
	RandomAccessFile raf;
	/***
	 * 日期 [info]
	 */
	String defaultFormat = "\r\n%s [%s] %s : %s \r\n";

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
			System.err.println();
			raf = new RandomAccessFile(new File(this.getClass().getResource("/").getFile() + dataFormat.format(new Date()) + "_log.txt"), "rws");
			// System.out.println(raf.length());
			// if (raf.length() < 10240000)
			// raf.seek(raf.length());
			// else
			// raf.setLength(0);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public synchronized Logger setInfoWrite(boolean isWrite) {
		this.infoWrite = isWrite;
		return this;
	}

	public synchronized Logger setErrorWrite(boolean isWrite) {
		this.errorWite = isWrite;
		return this;
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
		if (level == 0)
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
		return log(String.format(defaultFormat, new Date(), "INFO", clazz.getName(), msg), infoWrite, 0);
	}

	public Log info(Class<?> clazz, Throwable msg) {
		return log(throwableFormat(String.format(defaultFormat, new Date(), "EXCEPTION", clazz.getName(), ""), msg), infoWrite, 0);
	}

	public Log info(String className, Throwable msg) {
		return log(throwableFormat(String.format(defaultFormat, new Date(), "EXCEPTION", className, ""), msg), infoWrite, 0);
	}

	public Logger info(String className, String msg) {
		return log(String.format(defaultFormat, new Date(), "INFO", className, msg), infoWrite, 0);
	}

	private Logger write(String msg) throws IOException {
		raf.write(msg.getBytes());
		return this;
	}

	private String throwableFormat(String header, Throwable e) {
		StringBuffer sb = new StringBuffer();
		sb.append(header).append(e);
		e.printStackTrace();
		for (StackTraceElement traceElement : e.getStackTrace())
			sb.append("\tat" + traceElement + "\r\n");

		// // Print suppressed exceptions, if any
		for (Throwable se : e.getSuppressed())
			sb.append("\tat" + se.getMessage() + "\r\n");
		//
		// // Print cause, if any
		// Throwable ourCause = getCause();
		// if (ourCause != null)
		// ourCause.printEnclosedStackTrace(s, trace, CAUSE_CAPTION, "",
		// dejaVu);
		// System.err.println(sb.toString());
		return sb.toString();
	}

	public Logger error(Class<?> clazz, Throwable msg) {
		return log(throwableFormat(String.format(defaultFormat, new Date(), "EXCEPTION", clazz.getName(), ""), msg), errorWite, 1);
	}

	public Logger error(String className, Throwable msg) {
		return log(throwableFormat(String.format(defaultFormat, new Date(), "EXCEPTION", className, ""), msg), errorWite, 1);
	}

	public Logger error(Class<?> clazz, String msg) {
		return log(String.format(defaultFormat, new Date(), "EXCEPTION", clazz.getName(), msg), errorWite, 1);
	}

	public Logger error(String className, String msg) {
		return log(String.format(defaultFormat, new Date(), "EXCEPTION", className, msg), errorWite, 1);
	}

	public Log print(String className, String msg) {
		return log(String.format(defaultFormat, new Date(), "PRINT", className, msg), false, 0);
	}

	public static void main(String[] args) throws IOException {
		new Logger().info(Logger.class, "小明明小小明").error(Logger.class, new Throwable("小明明小小明"));
	}

}
