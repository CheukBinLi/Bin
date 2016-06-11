package com.cheuks.bin.anythingtest.thrift;

import org.apache.thrift.TException;

import java.util.ArrayList;
import java.util.List;

public class modelServerImpl implements modelServer.Iface {

	public model getModel(int id) throws notFindModel, TException {
		model m = new model();
		//		m.id = (Math.random()*1000) * 10;
		m.id = id;
		m.name = new String(new char[] { (char) (Math.random() * 255), (char) (Math.random() * 255), (char) (Math.random() * 255) });
		m.remark = "小喇叭";
		return m;
	}

	public List<model> getList() throws TException {
		List<model> list = new ArrayList<model>();
		model m;
		for (int i = 0; i < 100; i++) {
			m = new model();
			m.id = (int) (Math.random() * 1000);
			m.name = new String(new char[] { (char) (Math.random() * 1000), (char) (Math.random() * 1000), (char) (Math.random() * 1000) });
			m.remark = m.id + "：小喇叭";
			list.add(m);
		}
		return list;
	}

}
