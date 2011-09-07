package com.wordpress.innerp.sql;

import java.util.HashMap;
import java.util.Map;

public class Limit {
	
	private Map<String,String> map = new HashMap<String,String>();
	public Limit limit(int from,int offset){
		clear();
		map.put("word", SQL.LIMIT);
		map.put(String.valueOf(from), String.valueOf(offset));
		return this;
	}
	public Map<String, String> getMap() {
		return map;
	}
	
	public void clear(){
		map.clear();
	}
}
