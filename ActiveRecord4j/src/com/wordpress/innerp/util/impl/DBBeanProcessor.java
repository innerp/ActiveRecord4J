package com.wordpress.innerp.util.impl;

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
		result.put(TABLE_NO_PREFIX, bean.getClass().getSimpleName());
		return result;
	}
	
	public Map<String,Object> toInsertMap(Object bean){
		Map<String,Object> result = beantoMap(bean);
		result.put(ACTION,INSERT);
		String tableName =(result.get(PRE_FIX)==null?"":result.get(PRE_FIX).toString())+result.get(TABLE_NO_PREFIX).toString();
		result.remove(PRE_FIX);
		result.remove(TABLE_NO_PREFIX);
		result.put(TABLE, tableName);
		return result;
	}
	public Map<String,Object> toUpdate(Object bean){
		Map<String,Object> result = beantoMap(bean);
		result.put(ACTION,UPDATE);
		String tableName =(result.get(PRE_FIX)==null?"":result.get(PRE_FIX).toString())+result.get(TABLE_NO_PREFIX).toString();
		result.remove(PRE_FIX);
		result.remove(TABLE_NO_PREFIX);
		result.put(TABLE, tableName);
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
		String tableName =(result.get(PRE_FIX)==null?"":result.get(PRE_FIX).toString())+result.get(TABLE_NO_PREFIX).toString();
		result.remove(PRE_FIX);
		result.remove(TABLE_NO_PREFIX);
		result.put(TABLE, tableName);
		return result;
		
	}
	
	
	
	
	
	

}
