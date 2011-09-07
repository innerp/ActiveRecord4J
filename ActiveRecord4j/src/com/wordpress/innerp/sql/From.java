package com.wordpress.innerp.sql;

/**
 * 想支持多表查询
 * */
public class From {
	
	private String tables;
	
	public From from(String tables){
		this.tables = tables;
		return this;
	}
	public String getTables(){
		return tables;
	}
	public void clear(){
		tables = null;
	}

}
