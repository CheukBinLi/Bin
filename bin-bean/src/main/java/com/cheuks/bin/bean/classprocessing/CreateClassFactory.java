package com.cheuks.bin.bean.classprocessing;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtNewConstructor;
import javassist.bytecode.DuplicateMemberException;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class CreateClassFactory {

	private transient BlockingQueue<DefaultTempClass> errorQueue = new LinkedBlockingQueue<DefaultTempClass>();

	private static CreateClassFactory newInstance = new CreateClassFactory();

	private boolean initSystemClassLoader;

	public static CreateClassFactory newInstance() {
		return newInstance;
	}

	public void create(final CreateClassInfo classInfo, final boolean initSystemClassLoader, final boolean cloneModel) throws InterruptedException, InstantiationException, IllegalAccessException {
		//第一节
		DefaultTempClass tempClass;
		for (int i = 0, len = classInfo.getFirstQueue().size(); i < len; i++) {
			try {
				tempClass = classInfo.getFirstQueue().get(i);
				AbstractClassProcessingFactory.anthingToClass(tempClass.getNewClazz(), tempClass.getSuperClazz().getName(), initSystemClassLoader, cloneModel);
			} catch (CannotCompileException e) {
				e.printStackTrace();
			}
		}
		//第二节
		errorQueue.addAll(classInfo.getSecondQueue());
		while (errorQueue.size() > 0) {
			//建立构造、构造加载
			tempClass = errorQueue.poll();
			try {
				CtConstructor tempC;
				CtClass newClazz = tempClass.getNewClazz();
				CtConstructor[] ctConstructors = tempClass.getSuperClazz().getDeclaredConstructors();
				CtConstructor defauleConstructor = CtNewConstructor.defaultConstructor(newClazz);
				//				System.err.println(tempClass.getConstructor());
				if (null != tempClass)
					defauleConstructor.setBody(tempClass.getConstructor());

				//				defauleConstructor.addCatch("", newClazz.getClassPool().get("java.lang.Exception"));

				//################构造###################
				newClazz.addConstructor(defauleConstructor);

				try {
					for (CtConstructor c : ctConstructors) {
						tempC = CtNewConstructor.copy(c, newClazz, null);
						tempC.setBody("{super($$);}");
						newClazz.addConstructor(tempC);
					}
				} catch (DuplicateMemberException e) {
					//					e.printStackTrace();
				}
				AbstractClassProcessingFactory.anthingToClass(newClazz, tempClass.getSuperClazz().getName(), isInitSystemClassLoader(), cloneModel);
			} catch (Exception e) {
				e.printStackTrace();
				errorQueue.put(tempClass);
			}
		}
	}

	public boolean isInitSystemClassLoader() {
		return initSystemClassLoader;
	}

	public CreateClassFactory setInitSystemClassLoader(boolean initSystemClassLoader) {
		this.initSystemClassLoader = initSystemClassLoader;
		return this;
	}

}
