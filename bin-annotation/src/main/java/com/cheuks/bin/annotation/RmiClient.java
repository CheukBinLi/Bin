package com.cheuks.bin.annotation;

import java.lang.annotation.*;

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

	public long timeOut() default 10000L;

	/***
	 * 序列化实现
	 * @return
	 */
	//	public String Serializ() default "";
	public String Serializ() default "com.cheuks.bin.net.util.DefaultSerializImpl";

}
