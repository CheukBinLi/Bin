package com.cheuks.bin.anythingtest.xml;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class XmlEntity {

	public static class B {
		private String a;
		private String b;

		public String getA() {
			return a;
		}

		public B setA(String a) {
			this.a = a;
			return this;
		}

		public String getB() {
			return b;
		}

		public B setB(String b) {
			this.b = b;
			return this;
		}

	}

	private String a;
	private String b;

	private B bclass;

	public String getA() {
		return a;
	}

	public XmlEntity setA(String a) {
		this.a = a;
		return this;
	}

	public String getB() {
		return b;
	}

	public XmlEntity setB(String b) {
		this.b = b;
		return this;
	}

	public B getBclass() {
		return bclass;
	}

	public XmlEntity setBclass(B bclass) {
		this.bclass = bclass;
		return this;
	}

	public static void main(String[] args) throws NoSuchFieldException, SecurityException, InstantiationException, IllegalAccessException, ParserConfigurationException, SAXException, IOException {
		String xml = "<?xml version='1.0' encoding='UTF-8'?><XmlEntity><a>a</a><b>b</b><bclass><a>1</a><b>2</b></bclass></XmlEntity>";
		XmlReaderAll xra = new XmlReaderAll();
		XmlEntity xe = new XmlEntity();
		xe = xra.padding(xml.getBytes(), XmlEntity.class);
		System.err.println(xe.getBclass().getB());

	}

}
