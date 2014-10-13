/*
 * File Name: ReflectUtils.java 
 * History:
 * Created by Siyang.Miao on 2011-7-14
 */
package com.yucc.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class ReflectUtils {
	
	public static <T> T invokeStatic(Class<T> returnType, Class<?> receiverType, String methodName, 
			Class<?>[] paramTypes, Object[] params) {
		return invoke(returnType, receiverType, methodName, paramTypes, receiverType, params, false);
	}

	public static <T> T invokeStatic(Class<T> returnType, Class<?> receiverType, String methodName, 
			Class<?>[] paramTypes, Object[] params, boolean requestAccessiblity) {
		return invoke(returnType, receiverType, methodName, paramTypes, receiverType, params, requestAccessiblity);
	}
	
	public static <T> T invoke(Class<T> returnType, Class<?> receiverType, String methodName, Class<?>[] paramTypes, 
			Object receiver, Object[] params) {
		return invoke(returnType, receiverType, methodName, paramTypes, receiver, params, false);
	}
	
    @SuppressWarnings("unchecked")
    public static <T> T invoke(Class<T> returnType, Class<?> receiverType, String methodName, Class<?>[] paramTypes, 
    		Object receiver, Object[] params, boolean requestAccessiblity) {
        T res = null;
        try {
            Method method = receiverType.getDeclaredMethod(methodName, paramTypes);
            if (requestAccessiblity) {
            	method.setAccessible(true);
            }
            res = (T) method.invoke(receiver, params);
        } catch (Exception e) {
        } 
        
        return res;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getValue(Class<T> fieldType, Class<?> receiverType, String fieldName, Object receiver, 
            boolean requestAccessiblity) {
        T res = null;
        try {
            Field f = receiverType.getDeclaredField(fieldName);
            if (requestAccessiblity) {
                f.setAccessible(true);
            }
            res = (T) f.get(receiver); 
        } catch (Exception e) {
        } 
        
        return res;
    }
    
    //==========================================================================
    // Inner/Nested Classes
    //==========================================================================
}
