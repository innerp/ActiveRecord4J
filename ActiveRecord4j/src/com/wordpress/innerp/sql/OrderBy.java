package com.wordpress.innerp.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderBy {

	public final static String DESC = "desc";
	public final static String ASC ="asc";
	private List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	
	
	public void setList(List<Map<String, Object>> list) {
		this.list = list;
	}
	public OrderBy desc(String columnName){
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(DESC, columnName);
		list.add(map);
		return this;
	}
	public OrderBy asc(String columnName){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put(ASC,columnName);
		list.add(map);
		return this;
		
	}
	public List<Map<String, Object>> getList() {
		return list;
	}
	public void clear(){
		list.clear();
	}
}
