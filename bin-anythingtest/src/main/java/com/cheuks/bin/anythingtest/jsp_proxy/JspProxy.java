package com.cheuks.bin.anythingtest.jsp_proxy;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class JspProxy {

	public static String getProxy(String address) throws IOException {

		if (!address.startsWith("http://"))
			address = "http://" + address;
		URL url = new URL(address);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
		httpURLConnection.setRequestProperty("Accept-Charset:", "UTF-8");
		httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		InputStream in = httpURLConnection.getInputStream();
		int code = 0;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		while ((code = in.read()) != -1) {
			out.write(code);
		}
		return new String(out.toByteArray());
	}

}
