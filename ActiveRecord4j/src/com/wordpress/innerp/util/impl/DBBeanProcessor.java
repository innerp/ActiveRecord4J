package com.wordpress.innerp.util.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
/**
 * 
 * convert Bean to Map that adapter for db;
 * **/
public class DBBeanProcessor extends BeanProcessorImpl {

	
	public final static String TABLE_NO_PREFIX = "tableNOPrefix";
	public final static String PRE_FIX = "prefix";
	public final static String INSERT = "INSERT";
	public final static String UPDATE = "UPDATE";
	public final static String DELETE = "DELETE";
	public final static String SELECT = "SELECT";
	public final static String ACTION = "ACTION";
	public final static String TABLE = "TABLE";
	/**
	 * clean the class attribute
	 * */
	
	public Map<String, Object> beantoMap(Object bean){
		Map<String,Object> result = super.beantoMap(bean);
		
		if(result.containsKey("class")){
			result.remove("class");
		}
		Class claz = bean.getClass();
		String table = "";
		try {
			 table = (String) claz.getMethod("table", null).invoke(bean, null);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		result.put(TABLE, table);
		return result;
	}
	
	public Map<String,Object> toInsertMap(Object bean){
		Map<String,Object> result = beantoMap(bean);
		result.put(ACTION,INSERT);
		return result;
	}
	public Map<String,Object> toUpdate(Object bean){
		Map<String,Object> result = beantoMap(bean);
		result.put(ACTION,UPDATE);
		String[] keys = result.keySet().toArray(new String[result.size()]);
		for(String key:keys){
			Object temp = result.get(key);
			if(temp==null){
				result.remove(key);
			}
		}
		return result;
	}
	public Map<String,Object> toDelete(Object bean){
		Map<String,Object> result = beantoMap(bean);
		result.put(ACTION,DELETE);
		return result;
		
	}
	
	
	
	
	
	

}
