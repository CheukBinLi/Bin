package com.cheuks.bin.anythingtest.netty.order;

import java.io.Serializable;
import java.util.List;

public class Xmodel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String code;
	public String name;
	public List<Integer> x;

	public Xmodel(String code, String name, List<Integer> x) {
		super();
		this.code = code;
		this.name = name;
		this.x = x;
	}

	public String getCode() {
		return code;
	}

	public Xmodel setCode(String code) {
		this.code = code;
		return this;
	}

	public String getName() {
		return name;
	}

	public Xmodel setName(String name) {
		this.name = name;
		return this;
	}

	public List<Integer> getX() {
		return x;
	}

	public Xmodel setX(List<Integer> x) {
		this.x = x;
		return this;
	}

}
