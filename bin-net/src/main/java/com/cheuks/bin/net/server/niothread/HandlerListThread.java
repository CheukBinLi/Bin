package com.cheuks.bin.net.server.niothread;

import java.lang.reflect.Method;

import com.cheuks.bin.net.server.handler.ServiceHandler;
import com.cheuks.bin.util.ReflectionUtil;

public class HandlerListThread extends AbstractControlThread {

	@Override
	public void run() {
		ServiceHandler serviceHandler;
		while (!Thread.interrupted()) {
			try {
				// 搜索
				serviceHandler = HANDLER_LIST.takeFirst();
				if (null == serviceHandler)
					continue;
				Class<?> c = serviceHandler.getClass();
				if (null != cache.get4Map(cacheTag, c.getSimpleName()))
					continue;
				Method[] methods = c.getDeclaredMethods();
				for (Method m : methods)
					cache.addNFloop4Map(true, m, cacheTag, serviceHandler.classID(), ReflectionUtil.newInstance().getMethodName(m));
				SERVICE_HANDLER_MAP.put(serviceHandler.classID(), serviceHandler);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

}
