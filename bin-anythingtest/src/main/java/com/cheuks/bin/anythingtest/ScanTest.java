package com.cheuks.bin.anythingtest;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import com.cheuks.bin.bean.scan.Scan;

public class ScanTest {

	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
		URL u = Thread.currentThread().getContextClassLoader().getResource("");
		System.out.println(u);
		Set<String> paths = Scan.doScan("mapper", "query.xml", true);
		Iterator<String> it = paths.iterator();
		while (it.hasNext())
			System.err.println(it.next());

		// Set<String> pathss = Scan.doScan("/", ".xml", false);
		// Iterator<String> itt = pathss.iterator();
		// while (itt.hasNext())
		// System.err.println(itt.next());
		//
		// Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources("");
		// Set<URL> scanResult = new LinkedHashSet<URL>();
		// while (urls.hasMoreElements()) {
		// System.out.println(urls.nextElement());
		// }

	}

}
