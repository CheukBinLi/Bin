package com.cheuks.bin.bean.classprocessing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import com.cheuks.bin.annotation.Register;
import com.cheuks.bin.bean.application.BeanFactory;
import com.cheuks.bin.bean.classprocessing.handler.ClassProcessingHandler;
import com.cheuks.bin.bean.classprocessing.handler.DefaultAutoLoadHandler;
import com.cheuks.bin.bean.classprocessing.handler.DefaultInterceptHandler;
import com.cheuks.bin.bean.classprocessing.handler.DefaultRmiClientHandler;
import com.cheuks.bin.bean.classprocessing.handler.HandlerInfo;
import com.cheuks.bin.bean.util.ExecutorServiceFatory;
import com.cheuks.bin.bean.util.ShortNameUtil;
import com.cheuks.bin.bean.xml.DefaultConfigInfo;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMember;
import javassist.CtMethod;
import javassist.NotFoundException;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DefaultClassProcessingFactory extends AbstractClassProcessingFactory<CreateClassInfo> {

	public CreateClassInfo getCompleteClass(Set<String> clazzs, Object config) throws Throwable {
		final ConcurrentHashMap<String, CtClass> complete = new ConcurrentHashMap<String, CtClass>();
		final ConcurrentHashMap<String, String> nick = new ConcurrentHashMap<String, String>();
		final ConcurrentHashMap<String, String> shortName = new ConcurrentHashMap<String, String>();
		final CountDownLatch countDownLatch = new CountDownLatch(clazzs.size());
		Map<String, Map> cache = new HashMap<String, Map>();
		cache.put(ClassProcessingFactory.REGISTER_CACHE, complete);
		cache.put(ClassProcessingFactory.NICK_NAME_CACHE, nick);
		cache.put(ClassProcessingFactory.SHORT_NAME_CACHE, shortName);
		Iterator<String> it = clazzs.iterator();
		while (it.hasNext()) {
			ExecutorServiceFatory.SINGLE_EXECUTOR_SERVICE.execute(new Worker(countDownLatch, cache, it.next(), (DefaultConfigInfo) config));
		}
		countDownLatch.await();
		//
		List<ClassProcessingHandler> list = new ArrayList<ClassProcessingHandler>();
		list.add(new DefaultAutoLoadHandler());
		list.add(new DefaultInterceptHandler());
		list.add(new DefaultRmiClientHandler());
		return doHandler(list, cache);

	}

	class Worker implements Runnable {

		private CountDownLatch countDownLatch;
		private Map<String, Map> cache;
		private String className;
		private List<String> xmlAppendList;
		private DefaultConfigInfo configInfo;
		private boolean falgs;

		public Worker(CountDownLatch countDownLatch, Map<String, Map> cache, String className, DefaultConfigInfo configInfo) {
			super();
			this.countDownLatch = countDownLatch;
			this.cache = cache;
			this.className = className;
			this.configInfo = configInfo;
		}

		public Worker(CountDownLatch countDownLatch, Map<String, Map> cache, List<String> xmlAppendList, DefaultConfigInfo configInfo) {
			super();
			this.countDownLatch = countDownLatch;
			this.cache = cache;
			this.xmlAppendList = xmlAppendList;
			this.configInfo = configInfo;
			this.falgs = true;
		}

		public void run() {
			try {
				int count = 1;
				if (this.falgs) {
					count = xmlAppendList.size();
				}
				while (--count >= 0) {
					if (this.falgs)
						className = xmlAppendList.get(count);

					ClassPool pool = ClassPool.getDefault();
					CtClass clazz = pool.get(className);

					Object o = clazz.getAnnotation(Register.class);
					if (null == o || null != BeanFactory.getBean(clazz.getName(), configInfo.isCloneModel()))
						continue;
					String name = ((Register) o).value();
					if (name.length() > 0)
						cache.get(NICK_NAME_CACHE).put(name, clazz.getName());
					cache.get(REGISTER_CACHE).put(clazz.getName(), clazz);

					BeanFactory.addCache(clazz.getName(), ClassProcessingFactory.SHORT_NAME_CACHE, ShortNameUtil.makeShortName(clazz.getName()));
					//搜索class
					try {
						BeanFactory.addClassInfo(scanClass(Class.forName(className), true));
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
				}
				return;
			} catch (NotFoundException e) {
				//				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			} catch (CloneNotSupportedException e1) {
				e1.printStackTrace();
			} finally {
				if (null != countDownLatch)
					countDownLatch.countDown();
			}
		}
	}

	protected CreateClassInfo doHandler(List<ClassProcessingHandler> handler, final Map<String, Map> classCache) throws Throwable {

		final Map<String, String> autoLoadClass = new ConcurrentHashMap<String, String>();
		classCache.put(ClassProcessingFactory.AUTO_LOAD_CACHE, autoLoadClass);

		final CreateClassInfo result = new CreateClassInfo();

		Iterator<Entry<String, CtClass>> it = classCache.get(REGISTER_CACHE).entrySet().iterator();

		Entry<String, CtClass> en;
		int level = 0;
		List<HandlerInfo> handlerInfos = null;
		boolean appendTry;
		boolean isInterface;
		CtClass clone = ClassPool.getDefault().get("com.cheuks.bin.bean.classprocessing.CloneAdapter");
		StringBuffer cloneStr = new StringBuffer();
		CtClass cloneClass = ClassPool.getDefault().get("java.lang.Cloneable");
		while (it.hasNext()) {
			cloneStr.setLength(0);
			level = 0;
			handlerInfos = new ArrayList<HandlerInfo>();
			en = it.next();
			CtClass tempClazz = en.getValue();
			CtClass newClazz = tempClazz.getClassPool().makeClass(tempClazz.getName() + Impl);
			newClazz.getClassPool().importPackage(en.getKey());
			CtField[] ctFields = tempClazz.getDeclaredFields();
			CtMethod[] ctMethods = tempClazz.getDeclaredMethods();
			if (isInterface = tempClazz.isInterface())
				newClazz.addInterface(tempClazz);
			else
				newClazz.setSuperclass(tempClazz);
			newClazz.addInterface(clone);
			HandlerInfo handlerInfo;
			for (ClassProcessingHandler<CtClass, Object, CtMember, CtClass, HandlerInfo> cph : handler) {
				for (CtField f : ctFields) {//Field
					if (null != cph.getCheck(f, ClassProcessingHandler.Field) || null != cph.getCheckII(tempClazz, ClassProcessingHandler.Type)) {
						handlerInfo = cph.doProcessing(classCache, newClazz, f, null);
						if (null == handlerInfo)
							continue;
						handlerInfos.add(handlerInfo);
						level++;
					}
				}
				for (CtMethod m : ctMethods) {
					if (null != cph.getCheck(m, ClassProcessingHandler.Method) || null != cph.getCheckII(tempClazz, ClassProcessingHandler.Type)) {
						handlerInfo = cph.doProcessing(classCache, newClazz, m, null);
						if (null == handlerInfo)
							continue;
						handlerInfos.add(handlerInfo);
						newClazz = handlerInfo.getNewClazz();
						level++;
					}
				}
			}
			CtMethod ctClone = CtMethod.make("public Object clone() throws CloneNotSupportedException{return null;}", newClazz);
			cloneStr.append("{").append(newClazz.getName()).append(" o = null;");
			cloneStr.append("o = (").append(newClazz.getName()).append(") super.clone();");
			newClazz.addMethod(ctClone);
			for (CtField f : newClazz.getDeclaredFields()) {
				for (CtClass cc : f.getType().getInterfaces()) {
					if (cloneClass == cc) {
						cloneStr.append("o.").append(f.getName()).append(" = (").append(f.getType().getName()).append(")").append("o.").append(f.getName()).append(".clone();");
					}
				}
			}
			cloneStr.append("return o;}");
			ctClone.setBody(cloneStr.toString());
			newClazz.getClassPool().importPackage("java.lang.Exception");
			newClazz.getClassPool().importPackage("java.lang.reflect.Field");
			newClazz.getClassPool().importPackage("java.lang.reflect.Method");
			newClazz.getClassPool().importPackage("com.cheuks.bin.bean.application.BeanFactory");
			newClazz.getClassPool().importPackage("com.cheuks.bin.bean.classprocessing.ClassInfo");
			StringBuffer sb = new StringBuffer("{");
			sb.append("super($$);");
			appendTry = false;
			if (handlerInfos.size() > 0) {
				sb.append("try {");
				for (HandlerInfo h : handlerInfos) {
					if (null != h.getX() && h.getX().length() > 0) {
						sb.append(h.getX());
						appendTry = true;
					}
				}
				sb.append("}catch(java.lang.Exception e){e.printStackTrace();}");
			}
			sb.append("}");
			for (HandlerInfo h : handlerInfos) {
				if (null != h.getImports())
					for (String s : h.getImports()) {
						newClazz.getClassPool().importPackage(s);
					}
			}
			if (level == 0)
				result.addFirstQueue(new DefaultTempClass(tempClazz, newClazz));
			else
				result.addSecondQueue(new DefaultTempClass(tempClazz, newClazz, appendTry ? sb.toString() : null, null));
		}
		return result;
	}
}
