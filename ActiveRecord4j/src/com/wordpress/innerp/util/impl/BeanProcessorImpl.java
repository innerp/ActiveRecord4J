package com.wordpress.innerp.util.impl;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wordpress.innerp.util.BeanProcessor;
/**
 * 
 * convert bean to map or convert map to bean
 * **/
public  class BeanProcessorImpl implements BeanProcessor {

	
	public Map<String, Object> beantoMap(Object bean) {
		PropertyDescriptor[] descriptors = null;
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			descriptors = Introspector.getBeanInfo(bean.getClass())
					.getPropertyDescriptors();
			int size = descriptors.length;
			if (size == 0) return null;
			for (int i = 0; i < size; i++) {
				String name = descriptors[i].getName();
				Object value = descriptors[i].getReadMethod().invoke(bean, new Object[0]);
				result.put(name, value);
			}
			return result;
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public <T> T mapToBean(Map<String, Object> map, Class<T> bean) {
		PropertyDescriptor[] descriptors = null;
		try {
			descriptors = Introspector.getBeanInfo(bean).getPropertyDescriptors();
			if(map==null||map.size()==0)return null;
			T result = bean.newInstance();
			String[] keys = map.keySet().toArray(new String[map.size()]);
			for(String key:keys){
				for(PropertyDescriptor p:descriptors){
					String name = p.getName();
					if(name.equalsIgnoreCase(key)){
						p.getWriteMethod().invoke(result, map.get(key));
					    break;
					}
				}
			}
			return result;
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public <T> List<T> mapsToBeanList(List<Map<String, Object>> list,
			Class<T> bean) {
		List<T> results = new ArrayList<T>();
		int size = list.size();
		if(size==0)return null;
		for(int i=0;i<size;i++){
			results.add(mapToBean(list.get(i),bean));
		}
		return results;
	}

}
