package com.cheuks.bin.anythingtest.jsontest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class XEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String name;
	private String realName;
	private String sex;
	private Date birthday;
	private String address;
	private String phone;
	private int type;
	private boolean isOnline;
	private String remake;

	public int getId() {
		return id;
	}

	public XEntity setId(int id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public XEntity setName(String name) {
		this.name = name;
		return this;
	}

	public String getRealName() {
		return realName;
	}

	public XEntity setRealName(String realName) {
		this.realName = realName;
		return this;
	}

	public String getSex() {
		return sex;
	}

	public XEntity setSex(String sex) {
		this.sex = sex;
		return this;
	}

	public Date getBirthday() {
		return birthday;
	}

	public XEntity setBirthday(Date birthday) {
		this.birthday = birthday;
		return this;
	}

	public String getAddress() {
		return address;
	}

	public XEntity setAddress(String address) {
		this.address = address;
		return this;
	}

	public String getPhone() {
		return phone;
	}

	public XEntity setPhone(String phone) {
		this.phone = phone;
		return this;
	}

	public int getType() {
		return type;
	}

	public XEntity setType(int type) {
		this.type = type;
		return this;
	}

	public boolean isOnline() {
		return isOnline;
	}

	public XEntity setOnline(boolean isOnline) {
		this.isOnline = isOnline;
		return this;
	}

	public String getRemake() {
		return remake;
	}

	public XEntity setRemake(String remake) {
		this.remake = remake;
		return this;
	}

	public XEntity(int id, String name, String realName, String sex, Date birthday, String address, String phone, int type, boolean isOnline, String remake) {
		super();
		this.id = id;
		this.name = name;
		this.realName = realName;
		this.sex = sex;
		this.birthday = birthday;
		this.address = address;
		this.phone = phone;
		this.type = type;
		this.isOnline = isOnline;
		this.remake = remake;
	}

	public XEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static List<XEntity> createList(int size) {
		List<XEntity> result = new ArrayList<XEntity>();
		for (int i = 0; i < size; i++) {
			result.add(new XEntity(i, "小明" + i, "小小明" + i, i % 2 == 0 ? "男" : "女", new Date(), "adsfasdf23rwefdsfsdf" + i, "1312933912" + i, i, i % 2 == 0, null));
		}
		return result;
	}

}
