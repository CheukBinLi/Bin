package com.cheuks.bin.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface RmiClient {

	/***
	 * localhost:8080
	 * @return
	 */
	public String path();

	public String classID();

	/***
	 * 是否使用短连接
	 * @return
	 */
	public boolean shortConnect() default false;

}
