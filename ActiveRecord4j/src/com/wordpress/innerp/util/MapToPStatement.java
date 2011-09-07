package com.wordpress.innerp.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Map;

public interface MapToPStatement {
	
	public  PreparedStatement mapToStatementForUpdate(Map<String, Object> map,
			Connection conn);
	public PreparedStatement mapToStatementForInsert(Map<String, Object> map,
			Connection conn);
	public PreparedStatement mapToStatementForDelete(Map<String,Object> map,Connection conn);

}
