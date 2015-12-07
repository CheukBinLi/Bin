package com.cheuks.bin.anythingtest.bean.code_test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import com.cheuks.bin.anythingtest.bean.mc.scan.IocTest1;
import com.cheuks.bin.bean.application.BeanFactory;
import com.cheuks.bin.bean.classprocessing.ClassProcessingFactory;
import com.cheuks.bin.bean.classprocessing.CreateClassInfo;
import com.cheuks.bin.bean.classprocessing.DefaultClassProcessingFactory;
import com.cheuks.bin.bean.scan.Scan;

public class Scan_Classprocessing {

	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException, Throwable {
		//1.0
		//		old o = (old) ((ClazzInfo) new DefaultClassProcessing().classProcessing(old.class)).getClazz().newInstance();
		//		o.setFX("mba");
		//		System.out.println(o.getFX());
		//		o.x();
		//2.0

		ClassProcessingFactory<CreateClassInfo> classProcessingFactory = new DefaultClassProcessingFactory() {
		};

		CreateClassInfo result = classProcessingFactory.getCompleteClass(Scan.doScan("com.ben.mc.AnthingTest.*.*"), null);

		//		for (Entry<String, CtClass> en : result.entrySet())
		//			System.out.println(en.getValue().getName());

		//		Thread.sleep(50);
		//		AutoLoadTestI i = BeanFactory.getBean("autoLoadTestI");
		//		i.hello("王小牛");
		IocTest1 i = BeanFactory.getBean("iocTest1");
		i.aaxx("王小牛0_0");

	}

	static {

	}
}
