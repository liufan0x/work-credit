/*
 *Project: anjbo-credit3-common
 *File: com.anjbo.utils.ReflectHelper.java  <2018年2月24日>
 ****************************************************************
 * 版权所有@2018 ANJBO.COM  保留所有权利.
 ***************************************************************/
package com.anjbo.utils;

import java.lang.reflect.Field;

import org.apache.commons.lang3.reflect.FieldUtils;

/**
 * @Author KangLG 
 * @Date 2018年2月24日 下午12:51:40
 * @version 1.0
 */
public class ReflectUtils {
	
	public static Object getFieldValue(Object obj , String fieldName ){          
        if(obj == null){  
            return null ;  
        }            
        Field targetField = getTargetField(obj.getClass(), fieldName);  
          
        try {  
            return FieldUtils.readField(targetField, obj, true ) ;  
        } catch (IllegalAccessException e) {  
            e.printStackTrace();  
        }   
        return null ;  
    }  
      
    public static Field getTargetField(Class<?> targetClass, String fieldName) {  
        Field field = null;    
        try {  
            if (targetClass == null) {  
                return field;  
            }    
            if (Object.class.equals(targetClass)) {  
                return field;  
            }  
  
            field = FieldUtils.getDeclaredField(targetClass, fieldName, true);  
            if (field == null) {  
                field = getTargetField(targetClass.getSuperclass(), fieldName);  
            }  
        } catch (Exception e) {  
        }    
        return field;  
    }  
      
    public static void setFieldValue(Object obj , String fieldName , Object value ){  
        if(null == obj){return;}  
        Field targetField = getTargetField(obj.getClass(), fieldName);    
        try {  
             FieldUtils.writeField(targetField, obj, value) ;  
        } catch (IllegalAccessException e) {  
            e.printStackTrace();  
        }   
    }   

}
