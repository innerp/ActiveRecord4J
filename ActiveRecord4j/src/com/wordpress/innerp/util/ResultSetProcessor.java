package com.wordpress.innerp.util;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public interface ResultSetProcessor {
	
	
	
	public Map<String,Object> resultToMap(ResultSet rs);
	public List<Map<String,Object>> resultToMaps(ResultSet rs);
	

}
