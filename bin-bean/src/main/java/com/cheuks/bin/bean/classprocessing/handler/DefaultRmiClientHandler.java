package com.cheuks.bin.bean.classprocessing.handler;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.cheuks.bin.annotation.RmiClient;
import com.cheuks.bin.bean.classprocessing.ClassInfo;
import com.cheuks.bin.util.ReflectionUtil;

import javassist.CtClass;
import javassist.CtField;
import javassist.CtMember;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;

public class DefaultRmiClientHandler extends AbstractClassProcessingHandler<CtClass, RmiClient> {

	public Class<RmiClient> handlerClass() {
		return RmiClient.class;
	}

	public Set<Integer> thisType() {
		return new HashSet<Integer>(Arrays.asList(Type));
	}

	String imp = "private com.cheuks.bin.net.server.handler.CallMethod callMethod = new com.cheuks.bin.net.server.handler.CallMethod(\"%s\",%d,%b,%d,new %s());";

	public HandlerInfo doProcessing(Map<String, Map> cache, CtClass newClazz, CtMember additional, Object config) throws Throwable {

		try {
			if (null == newClazz.getDeclaredField("callMethod")) {
				String[] path = this.a.path().split(":");
				if (path.length != 2) {
					return null;
				}
				newClazz.addField(CtField.make(String.format(imp, path[0], Integer.valueOf(path[1]), this.a.shortConnect(), this.a.timeOut(), this.a.Serializ()), newClazz));
			}
		} catch (NotFoundException e) {
			String[] path = this.a.path().split(":");
			if (path.length != 2) {
				return null;
			}
			newClazz.addField(CtField.make(String.format(imp, path[0], Integer.valueOf(path[1]), this.a.shortConnect(), this.a.timeOut(), this.a.Serializ()), newClazz));
		}
		CtMethod ctMethod = CtNewMethod.copy((CtMethod) additional, newClazz, null);
		boolean isReturn = !ctMethod.getReturnType().getName().equals("void");
		String result = "callMethod.call(\"" + this.a.classID() + "\",\"" + ReflectionUtil.newInstance().getMethodName(ctMethod) + "\"," + (ctMethod.getParameterTypes().length > 0 ? "$args);" : "null);") + "} catch (java.lang.Throwable e) {e.printStackTrace();}";
		StringBuffer sb = new StringBuffer("{try {");
		if (isReturn)
			sb.append("return (" + ctMethod.getReturnType().getName() + ")").append(result).append("return ").append(ClassInfo.getReturn(ctMethod.getReturnType())).append(";");
		else
			sb.append(result);
		sb.append("}");
		//		System.out.println(sb.toString());
		ctMethod.setBody(sb.toString());
		newClazz.addMethod(ctMethod);
		return new HandlerInfo(null, newClazz, ctMethod);

	}
}
