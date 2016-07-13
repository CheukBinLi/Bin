package com.cheuks.bin.local.dbmanager;

import com.cheuks.bin.db.manager.HibernateSqlParseHelpper;

import Controller.entity.Dict;

public class HibernateSqlParseHelpperTest {

	public static void main(String[] args) {
		Dict d = new Dict();
		d.setKey("x").setValue("y");
		System.out.println(HibernateSqlParseHelpper.newInstance().insert(d));
	}
}
