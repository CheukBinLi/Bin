package com.cheuks.bin.anythingtest.netty.packagemessage;

/***
 * *
 * 
 * Copyright 2015    CHEUK.BIN.LI Individual All
 *  
 * ALL RIGHT RESERVED
 *  
 * CREATE ON 2016年4月5日下午3:00:27
 *  
 * EMAIL:20796698@QQ.COM
 *  
 * GITHUB:https://github.com/fdisk123
 * 
 * @author CHEUK.BIN.LI
 * 
 * @see  连接服务类型
 *
 */
public enum ServiceType {

	/**
	 * 心跳服务
	 */
	SERVICE_TYPE_HEARTBEAT(0),
	/***
	 * 普通数据服务
	 */
	SERVICE_TYPE_SERVICE(1),
	/***
	 * 自定义类型，必须调用 CUSTOM(int)方法创建。
	 */
	SERVICE_TYPE_CUSTOM(999);

	private int value;

	ServiceType(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	private ServiceType setValue(int value) {
		this.value = value;
		return this;
	}

	public static final ServiceType CUSTOM(int value) {
		return ServiceType.SERVICE_TYPE_CUSTOM.setValue(value);
	}

}