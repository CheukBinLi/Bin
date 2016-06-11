package com.cheuks.bin.anythingtest.log;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class t1 {
	private static final Logger logger = LoggerFactory.getLogger(t1.class);

	public static void main(String[] args) {
		PropertyConfigurator.configure("");
		logger.info("Hello world");
		logger.error("ERROR");
	}
}
