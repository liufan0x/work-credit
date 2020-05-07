package com.anjbo.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.cglib.beans.BeanMap;

public class BeanToMapUtil{

	public static <T> Map<String, Object> beanToMap(T bean) {  
		Map<String, Object> map = new HashMap<String, Object>();
		if (bean != null) {  
			BeanMap beanMap = BeanMap.create(bean);  
			for (Object key : beanMap.keySet()) {
				Object tempValue = beanMap.get(key);
				if(tempValue != null){
					if(isMatch(tempValue)){
						map.put(key+"", tempValue.toString().substring(0, tempValue.toString().indexOf("."))); 
					}else{
						map.put(key+"", tempValue);  
					}
				}
			}
		}
		return map;  
	}
	
	/**
	 * Map转实体类
	 * @param type
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Object mapToBean(Class type,Map map)throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdfSort = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BeanInfo beanInfo = Introspector.getBeanInfo(type);
		Object obj = type.newInstance();
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
			String propertyName = propertyDescriptor.getName();
			if(map.containsKey(propertyName)){
				Object value = map.get(propertyName);
				if(propertyDescriptor.getPropertyType().getSimpleName().equals("Integer")){
					propertyDescriptor.getWriteMethod().invoke(obj,new Integer((String)value) );
				}else if(propertyDescriptor.getPropertyType().getSimpleName().equals("Date")){
					String v = (String)value;
					if(v.length()>=18){
						propertyDescriptor.getWriteMethod().invoke(obj,sdf.parse((String)(value)));
					}else{
						propertyDescriptor.getWriteMethod().invoke(obj,sdfSort.parse((String)(value)));
					}
				}else if(propertyDescriptor.getPropertyType().getSimpleName().equals("Double")){
					propertyDescriptor.getWriteMethod().invoke(obj,new Double((String)value) );
				}else if(propertyDescriptor.getPropertyType().getSimpleName().equals("BigDecimal")){
					propertyDescriptor.getWriteMethod().invoke(obj,new BigDecimal((String)value) );
				}else{
					propertyDescriptor.getWriteMethod().invoke(obj,value );
				}
			}
		}
		return obj;
	}
	
	public static boolean isMatch(Object tempValue){
		Pattern  pattern = Pattern.compile("^(([0-9]{1}\\d*)(\\.[0]{1}))$"); 
		return pattern.matcher(tempValue.toString()).matches();
	}
	
	public static void main(String[] args) {
		System.out.println(isMatch(0));
		System.out.println(isMatch(0.0));
		System.out.println(isMatch(0.00));
		System.out.println(isMatch(20.0));
		System.out.println(isMatch(0.03));
		System.out.println(isMatch(0.9));
		System.out.println(isMatch(0.89));
		System.out.println(isMatch(23231.89));
		System.out.println(isMatch(1.89));
	}
}
