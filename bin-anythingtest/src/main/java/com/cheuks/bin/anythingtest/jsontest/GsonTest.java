package com.cheuks.bin.anythingtest.jsontest;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GsonTest extends AbstractTestting {

	private List<XEntity> xEntity = XEntity.createList(1000);

	@Override
	void process() {
		Gson g = new Gson();
		String A = g.toJson(xEntity);
//		System.out.println(A);
		xEntity = g.fromJson(A, new TypeToken<List<XEntity>>() {
		}.getType());

	}

	public static void main(String[] args) {
		new GsonTest().testting();
	}

}
