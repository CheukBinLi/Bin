package com.cheuks.bin.anythingtest.spider;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class demo1 {

	public static String getContent(String urlPath) throws IOException {
		URL url;
		OutputStream out;
		InputStream in;
		if (urlPath.matches("^(http://|https://)+.*")) {
			url = new URL(urlPath);
		}
		else
			url = new URL("http://" + urlPath);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setConnectTimeout(10000);
		connection.setDoInput(true);
		connection.setRequestMethod("HEAD");
		connection.connect();
		in = connection.getInputStream();
		int code = 0;
		StringBuffer sb = new StringBuffer();
		while ((code = in.read()) != -1) {
			sb.append((char) code);
		}
		int length = connection.getContentLength();
		Map<String, List<String>> headers = connection.getHeaderFields();
		for (Entry<String, List<String>> en : headers.entrySet()) {
			System.out.println(en.getKey());
			if (null != en.getValue())
				for (String str : en.getValue()) {
					System.out.println("-------------" + str);
				}
		}
		Date lastModified = new Date(connection.getLastModified());
		System.err.println(String.format("最后更新时间:%tY-%tm-%td %tT", lastModified, lastModified, lastModified, lastModified));
		System.err.println(String.format("ContentLength:%d  sb.length:%d", length, sb.length()));
		RandomAccessFile raf = new RandomAccessFile(new File("C:/Users/Ben/Desktop/robot.html"), "rw");
		raf.writeBytes(sb.toString());
		raf.close();
		in.close();
		return sb.toString();
	}

	public static void main(String[] args) throws IOException {

		//		getContent("https://a");

		//		System.out.println("网易");
		//		getContent("http://www.163.com");
		//				System.out.println("新浪");
		//				getContent("http://www.sina.com");
		System.out.println("京东");
		//		getContent("http://www.jd.com");
		getContent("http://sale.jd.com/act/AWhnH15p2qVJ.html?cpdad=1DLSUE");

	}

}
