package com.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解用于标识需要产生防止重复提交的controller中的方法
 * @author 吴福明
 *
 */

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Token {
	
	boolean save() default false;

	boolean remove() default false;
	
}