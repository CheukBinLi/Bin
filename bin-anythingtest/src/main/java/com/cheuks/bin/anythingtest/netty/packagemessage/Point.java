package com.cheuks.bin.anythingtest.netty.packagemessage;

import java.lang.annotation.*;

/***
 * 
 * Copyright 2015    CHEUK.BIN.LI Individual All
 *  
 * ALL RIGHT RESERVED
 *  
 * CREATE ON 2015年11月17日 下午1:33:00
 *  
 * EMAIL:20796698@QQ.COM
 *  
 * GITHUB:https://github.com/fdisk123
 * 
 * @author CHEUK.BIN.LI
 * 
 * @see 服务点
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Point {

	/***
	 * 唯一ID
	 * @return
	 */
	public String UID();

	/***
	 * 版本号
	 * @return
	 */
	public String VERSION() default "1";
}
