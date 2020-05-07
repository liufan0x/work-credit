package com.anjbo.common.aop;

import java.lang.annotation.*;

/**
 * 校验注解
 * @author limh limh@anjbo.com   
 * @date 2017-12-28 上午10:39:00
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD })
public @interface ValidateAnt {
	/**
	 * 方法标识
	 * @return
	 */
	String name() default "";
	/**
	 * 方法描述
	 * @return
	 */
	String nameDesc() default "";
	/**
	 * 是否有次数限制（默认无）
	 * @return
	 */
	boolean limit() default false;
	/**
	 * 限制次数（默认20次）
	 * @return
	 */
	int limitCount() default 20;
	/**
	 * 限制时长（单位：秒，默认1天）
	 * @return
	 */
	int limitSecondsTime() default 24*60*60;
}
