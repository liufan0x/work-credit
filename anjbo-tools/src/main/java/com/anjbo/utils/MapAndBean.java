package com.anjbo.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtilsBean;

public class MapAndBean {
	
	/**
	 * Map转Bean
	 * @user lic
	 * @date 2016-11-17 下午06:24:01 
	 * @param type
	 * @param map
	 * @return
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 */
	@SuppressWarnings("rawtypes")
	public static Object MapToBean(Class type, Map<String, Object> map)  
	throws IntrospectionException, IllegalAccessException,  
	InstantiationException, InvocationTargetException {  
		BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性  
		Object obj = type.newInstance(); // 创建 JavaBean 对象  

		// 给 JavaBean 对象的属性赋值  
		PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();  
		for (int i = 0; i< propertyDescriptors.length; i++) {  
			PropertyDescriptor descriptor = propertyDescriptors[i];  
			String propertyName = descriptor.getName();  

			if (map.containsKey(propertyName)) {  
				// 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。  
				Object value = map.get(propertyName);  

				Object[] args = new Object[1];  
				args[0] = value;  

				descriptor.getWriteMethod().invoke(obj, args);  
			}  
		}  
		return obj;  
	}  
	
    /**
     * bean转Map
     * @user lic
     * @date 2016-11-17 下午06:24:43 
     * @param obj
     * @return
     */
    public static Map<String, Object> beanToMap(Object obj) { 
            Map<String, Object> params = new HashMap<String, Object>(0); 
            try { 
                PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean(); 
                PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj); 
                for (int i = 0; i < descriptors.length; i++) { 
                    String name = descriptors[i].getName(); 
                    if (!"class".equals(name)) { 
                        params.put(name, propertyUtilsBean.getNestedProperty(obj, name)); 
                    } 
                } 
            } catch (Exception e) { 
                e.printStackTrace(); 
            } 
            return params; 
    }
}
