package com.wordpress.innerp.sql;

public class Select {
	
	
	private String columns;
	public void select(String columns){
		this.columns =  columns;
		
	}
	public String getColumns() {
		return columns;
	}
	public void clear(){
		columns =null;
	}
	

}
