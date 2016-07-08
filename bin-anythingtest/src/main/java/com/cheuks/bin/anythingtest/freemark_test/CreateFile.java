package com.cheuks.bin.anythingtest.freemark_test;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Controller.entity.Permission;
import Controller.entity.Role;
import Controller.entity.RolePermission;
import Controller.entity.User;
import Controller.entity.UserRole;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;

public class CreateFile {

	public static void create(Class<?> c, Class<?> idType, boolean SimpleName) throws IOException, TemplateException {

		// dao
		// daoImpl
		// service
		// service
		// controller

		Configuration config = new Configuration(Configuration.VERSION_2_3_0);
		String path = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "com/cheuks/bin/anythingtest/freemark_test/";
		System.err.println(path);
		String gen = System.getProperty("user.dir") + "/gen";
		FileTemplateLoader fileTemplateLoader = new FileTemplateLoader(new File(path));
		config.setTemplateLoader(fileTemplateLoader);
		String[] flvs = { "Dao", "DaoImpl", "Service", "ServiceImpl", "query", "Controller" };
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("entityFullName", c.getName());
		map.put("entitySimpleName", c.getSimpleName());
		map.put("entityName", SimpleName ? c.getSimpleName() : c.getName());

		map.put("entityPackage", c.getPackage().getName().substring(0, c.getPackage().getName().lastIndexOf(".")));
		map.put("idType", SimpleName ? idType.getSimpleName() : idType.getName());
		map.put("idTypeSimpleName", idType.getSimpleName());
		map.put("tag", "#");
		map.put("params", getFieldWidthGetSetting(c));
		FileWriter writer;
		File genFile;
		for (String str : flvs) {
			// gen/user_Dao.java
			genFile = new File(gen + "/" + c.getSimpleName());
			if (!genFile.exists())
				genFile.mkdirs();
			if (!str.equals("query"))
				writer = new FileWriter(genFile = new File(String.format("%s/%s/%s%s.java", gen, c.getSimpleName(), c.getSimpleName(), str)));
			else writer = new FileWriter(new File(String.format("%s/%s/%s.%s.xml", gen, c.getSimpleName(), c.getSimpleName(), str)));
			config.getTemplate(str.toLowerCase() + ".flv").process(map, writer);
			writer.flush();
			writer.close();
		}
	}

	private static List<String> getFieldWidthGetSetting(Class<?> c) {
		Field[] fields = c.getDeclaredFields();
		Map<String, Field> map = new HashMap<String, Field>();
		List<String> list = new ArrayList<String>();
		for (Field f : fields) {
			if (f.getModifiers() == Modifier.PRIVATE && f.getModifiers() != Modifier.STATIC && f.getModifiers() != Modifier.TRANSIENT) {
				map.put(f.getName(), f);
			}
		}
		Method[] methods = c.getDeclaredMethods();
		String get;
		for (Method m : methods) {
			if (m.getModifiers() == Modifier.PUBLIC && m.getModifiers() != Modifier.STATIC && m.getName().startsWith("get")) {
				get = toLowerCaseFirstOne(m.getName().substring(3));
				if (map.containsKey(get)) {
					list.add(get);
				}
			}
		}
		return list;
	}

	public static String toUpperCaseFirstOne(String name) {
		char[] ch = name.toCharArray();
		ch[0] = Character.toUpperCase(ch[0]);
		return new String(ch);
	}

	public static String toLowerCaseFirstOne(String name) {
		char[] ch = name.toCharArray();
		ch[0] = Character.toLowerCase(ch[0]);
		return new String(ch);
	}

	public static void main(String[] args) throws IOException, TemplateException {
		// CreateFile.create(Dictionary.class, Integer.class, true);
		// CreateFile.create(HouseInfo.class, String.class, true);
		// CreateFile.create(HouseSteward.class, String.class, true);
		// CreateFile.create(Order.class, String.class, true);
		// CreateFile.create(UserInfo.class, String.class, true);
		// CreateFile.create(Notice.class, String.class, true);
		// CreateFile.create(User.class, String.class, true);
		// CreateFile.create(Dictionary.class, Integer.class, true);
		// CreateFile.create(HouseInfo.class, String.class, true);
		// CreateFile.create(HouseSteward.class, String.class, true);
		// CreateFile.create(Order.class, String.class, true);
		// CreateFile.create(UserInfo.class, String.class, true);
		// CreateFile.create(Notice.class, String.class, true);
		// CreateFile.create(User.class, String.class, true);
		create(User.class, int.class, true);
		create(Role.class, int.class, true);
		create(RolePermission.class, int.class, true);
		create(UserRole.class, int.class, true);
		create(Permission.class, int.class, true);
	}

}
