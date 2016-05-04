package com.cheuks.bin.anythingtest.freemark_test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;

public class CreateFile {

	public void create(Class<?> c, Class<?> idType, boolean SimpleName) throws IOException, TemplateException {

		// dao
		// daoImpl
		// service
		// service
		// controller

		Configuration config = new Configuration(Configuration.VERSION_2_3_0);
		String path = this.getClass().getClassLoader().getResource("").getPath() + "com/cheuks/bin/anythingtest/freemark_test/";
		System.err.println(path);
		String gen = System.getProperty("user.dir") + "/gen";
		FileTemplateLoader fileTemplateLoader = new FileTemplateLoader(new File(path));
		config.setTemplateLoader(fileTemplateLoader);
		String[] flvs = { "Dao", "DaoImpl", "Service", "ServiceImpl", "queue", "Controller" };
		Map<String, String> map = new HashMap<String, String>();
		map.put("entityFullName", c.getName());
		map.put("entitySimpleName", c.getSimpleName());
		map.put("entityName", SimpleName ? c.getSimpleName() : c.getName());
		map.put("idType", SimpleName ? idType.getSimpleName() : idType.getName());
		FileWriter writer;
		File genFile;
		for (String str : flvs) {
			// gen/user_Dao.java
			genFile = new File(gen + "/" + c.getSimpleName());
			if (!genFile.exists())
				genFile.mkdirs();
			if (!str.equals("queue"))
				writer = new FileWriter(genFile = new File(String.format("%s/%s/%s%s.java", gen, c.getSimpleName(), c.getSimpleName(), str)));
			else
				writer = new FileWriter(new File(String.format("%s/%s/%s.%s.xml", gen, c.getSimpleName(), c.getSimpleName(), str)));
			config.getTemplate(str.toLowerCase() + ".flv").process(map, writer);
			writer.flush();
			writer.close();
		}
	}

	public static void main(String[] args) throws IOException, TemplateException {
		new CreateFile().create(CreateFile.class, Integer.class, false);
	}

}
