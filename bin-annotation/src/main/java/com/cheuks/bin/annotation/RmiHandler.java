package com.cheuks.bin.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RmiHandler {

	/***
	 * 绑定端口
	 * 默认65000
	 * @return
	 */
	public int bindPort() default 65000;

}
