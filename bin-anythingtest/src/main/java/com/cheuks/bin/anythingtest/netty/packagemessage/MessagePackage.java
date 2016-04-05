package com.cheuks.bin.anythingtest.netty.packagemessage;

import java.io.Serializable;

/***
 * *
 * 
 * Copyright 2015    CHEUK.BIN.LI Individual All
 *  
 * ALL RIGHT RESERVED
 *  
 * CREATE ON 2016年4月5日下午3:03:02
 *  
 * EMAIL:20796698@QQ.COM
 *  
 * GITHUB:https://github.com/fdisk123
 * 
 * @author CHEUK.BIN.LI
 * 
 * @see 数据结构
 *
 */
@SuppressWarnings("rawtypes")
public class MessagePackage<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private final int head = 0xabcf;
	private final int end = 0xfcba;

	private ServiceType serviceType;

	private T messageBody;

	public ServiceType getServiceType() {
		return serviceType;
	}

	public MessagePackage setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
		return this;
	}

	public T getMessageBody() {
		return messageBody;
	}

	public MessagePackage setMessageBody(T messageBody) {
		this.messageBody = messageBody;
		return this;
	}

	public int getHead() {
		return head;
	}

	public int getEnd() {
		return end;
	}

}
