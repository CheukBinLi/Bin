package com.cheuks.bin.anythingtest.jsontest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JacksonTest extends AbstractTestting {

	private List<XEntity> xEntity = XEntity.createList(1000);

	@Override
	void process() {
		ObjectMapper om = new ObjectMapper();
		try {
			String A = om.writeValueAsString(xEntity);
			//			System.out.println(A);
			JavaType jtype = om.getTypeFactory().constructParametricType(ArrayList.class, XEntity.class);
			xEntity = om.readValue(A, jtype);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new JacksonTest().testting();
	}

}
