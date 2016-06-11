package com.cheuks.bin.annotation;

import java.lang.annotation.*;

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
