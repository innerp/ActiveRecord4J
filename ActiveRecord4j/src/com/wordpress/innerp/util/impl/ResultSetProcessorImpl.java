package com.wordpress.innerp.util.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.wordpress.innerp.util.ResultSetProcessor;
/**
 * convert ResultSet to map;
 * */
public class ResultSetProcessorImpl implements ResultSetProcessor {


	@Override
	public Map<String, Object> resultToMap(ResultSet rs) {
		ResultSetMetaData rsm = null;
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			rsm = rs.getMetaData();
			if (rs.next()) {
				int count = rsm.getColumnCount();
				for (int i = 0; i < count; i++) {
					Object temp = rs.getObject(i + 1);
					result.put(rsm.getColumnName(i + 1), temp);
				}
			}else{
				return null;
			}
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<Map<String, Object>> resultToMaps(ResultSet rs) {
		ResultSetMetaData rsm = null;
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			rsm = rs.getMetaData();
			int count = rsm.getColumnCount();
			while(rs.next()){
				Map<String, Object> bean = new HashMap<String, Object>();
				for (int i = 0; i < count; i++) {
					Object temp = rs.getObject(i + 1);
					bean.put(rsm.getColumnName(i + 1), temp);
				}
				list.add(bean);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

}
