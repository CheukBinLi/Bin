package com.cheuks.bin.base.web.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.GsonBuilder;

public class GridMessage extends Message {

	private int total;
	private List<?> data;
	private Set<String> withOut;

	@Override
	public String toJson() {
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
		builder.setExclusionStrategies(new ExclusionStrategy() {
			public boolean shouldSkipClass(Class<?> arg0) {
				return false;
			}

			public boolean shouldSkipField(FieldAttributes arg0) {
				if (null == withOut || withOut.isEmpty())
					return false;
				return withOut.contains(arg0.getName());
			}
		});

		return super.toJson();
	}

	public GridMessage(int code, String msg) {
		super(code, msg);
	}

	public GridMessage(int total, List<?> data, String... withOut) {
		super(null != data);
		this.total = total;
		this.data = data;
		if (null != withOut)
			this.withOut = new HashSet<String>(Arrays.asList(withOut));
	}

	public GridMessage(int total, List<?> data) {
		super(null != data);
		this.total = total;
		this.data = data;
	}

	public int getTotal() {
		return total;
	}

	public GridMessage setTotal(int total) {
		this.total = total;
		return this;
	}

	public List<?> getData() {
		return data;
	}

	public GridMessage setData(List<?> data) {
		this.data = data;
		return this;
	}

	public Set<String> getWithOut() {
		return withOut;
	}

	public GridMessage setWithOut(Set<String> withOut) {
		this.withOut = withOut;
		return this;
	}
}
