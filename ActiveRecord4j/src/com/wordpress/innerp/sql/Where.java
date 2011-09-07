package com.wordpress.innerp.sql;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * 
 * 每个条件的map结构<br>
 * 比如eq<br>
 * key       value <br>
 * keywords   = <br>
 * columnName arg
 * 
 * */
public class Where {
	
	public final static int EQUAL = 1;
	public final static int GREATER_THAN =2;
	public final static int GREATER_THAN_OR_EQUAL= 3;
	public final static int AND =4;
	public final static int OR = 5;
	public final static int LEASS_THAN = 6;
	public final static int LEASS_THAN_OR_EQUAL=7;
	public final static int LIKE = 8;
	private List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	public Where eq(String columnName,Object arg){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("word",EQUAL);
		map.put(columnName, arg);
		list.add(map);
		return this;
	}
	public Where and(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("word",AND);
		list.add(map);
		return this;
	}
	public Where or(){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("word",OR);
		list.add(map);
		return this;
	}
	//greater than
	public Where gt(String columnName,Object arg){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("word",GREATER_THAN);
		map.put(columnName, arg);
		list.add(map);
		return this;
	}
	//>=
	public Where gtOreq(String columnName,Object arg){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("word",GREATER_THAN_OR_EQUAL);
		map.put(columnName, arg);
		list.add(map);
		return this;
	}
	//less than
	public Where lt(String columnName,Object arg){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("word",LEASS_THAN);
		map.put(columnName, arg);
		list.add(map);
		return this;
	}
	//<=
	public Where ltOreq(String columnName,Object arg){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("word",LEASS_THAN_OR_EQUAL);
		map.put(columnName, arg);
		list.add(map);
		return this;
	}
	public Where like(String columnName,Object arg){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("word",LIKE);
		map.put(columnName, arg);
		list.add(map);
		return this;
	}
	public void clear(){
		list.clear();
	}
	public List<Map<String, Object>> getList() {
		return list;
	}
	
	

}
