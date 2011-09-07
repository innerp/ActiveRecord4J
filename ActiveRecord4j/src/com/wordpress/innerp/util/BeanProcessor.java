package com.wordpress.innerp.util;

import java.util.List;
import java.util.Map;

public interface BeanProcessor {
	
	
	/**
	 * we focus on the attributes which have set and get method,
	 * then the  attribute's name as the key ,it's value as Map value.
	 * */
	public Map<String,Object> beantoMap(Object bean);
	/**
	 * 
	 * generate a bean 
	 * */
	public <T> T mapToBean(Map<String,Object> map,Class<T> bean);
	
	/**
	 * generate bean list
	 * */
	
	public <T> List<T>  mapsToBeanList(List<Map<String,Object>> list,Class<T> bean);

}
