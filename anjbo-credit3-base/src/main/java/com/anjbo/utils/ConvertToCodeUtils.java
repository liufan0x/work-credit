package com.anjbo.utils;

import java.util.Map;

import org.apache.commons.jexl2.Expression;
import org.apache.commons.jexl2.JexlContext;
import org.apache.commons.jexl2.JexlEngine;
import org.apache.commons.jexl2.MapContext;

public class ConvertToCodeUtils {

	/**
	 * 实现将字符串变成可执行代码的功能
	 * @param expression
	 * @param obj
	 * @return
	 */
	public static Object convertToCode(String expression, Object obj) {
		Map<String, Object> params = BeanToMapUtil.beanToMap(obj);
		JexlEngine jexl = new JexlEngine();
		Expression e = jexl.createExpression(expression);
		JexlContext jc = new MapContext();
		for (String key : params.keySet()) {
			jc.set(key, params.get(key));
		}
		return e.evaluate(jc)==null ? "" : e.evaluate(jc);
	}

}
