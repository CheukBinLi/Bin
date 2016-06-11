package com.cheuks.bin.annotation;

import java.lang.annotation.*;

/***
 * 
 * Copyright 2015    CHEUK.BIN.LI Individual All
 *  
 * ALL RIGHT RESERVED
 *  
 * CREATE ON 2015年11月17日 下午1:32:31
 *  
 * EMAIL:20796698@QQ.COM
 *  
 * GITHUB:https://github.com/fdisk123
 * 
 * @author CHEUK.BIN.LI
 * 
 * @see 自动装载
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface AutoLoad {

	public String value() default "";

}
