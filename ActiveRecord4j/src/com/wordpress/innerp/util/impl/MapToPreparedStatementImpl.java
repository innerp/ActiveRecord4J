package com.wordpress.innerp.util.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.logging.Logger;

import com.wordpress.innerp.sql.SQL;
import com.wordpress.innerp.util.MapToPStatement;

/**
 * 
 * just for insert or update
 * */
public class MapToPreparedStatementImpl implements MapToPStatement{

	private static Logger log = Logger.getLogger("MapToPreparedStatement");
	private boolean showsql = true;

	public PreparedStatement mapToStatementForUpdate(Map<String, Object> map,
			Connection conn) {

		String sql = (String) map.get(DBBeanProcessor.ACTION) + "  "
				+ map.get(DBBeanProcessor.TABLE).toString() + "  SET ";
		map.remove(DBBeanProcessor.TABLE);
		map.remove(DBBeanProcessor.ACTION);
		long id = (Long) map.get("id");
		map.remove("id");
		String[] keys = map.keySet().toArray(new String[map.size()]);
		for (int i = 0; i < keys.length; i++) {
			sql += (keys[i] + "=? ");
			if (i < keys.length - 1) {
				sql += ",";
			}
		}
		sql += (" WHERE id=" + id);
		if (showsql) {
			log.info(sql);
		}
		try {
			PreparedStatement ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			for(int i=0;i<keys.length;i++){
				ps.setObject(i+1,map.get(keys[i]));
			}
			return ps;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public PreparedStatement mapToStatementForInsert(Map<String, Object> map,
			Connection conn) {
		String sql = (String) map.get(DBBeanProcessor.ACTION) + " INTO "
				+ map.get(DBBeanProcessor.TABLE).toString() + "(";
		map.remove(DBBeanProcessor.ACTION);
		map.remove(DBBeanProcessor.TABLE);
		map.remove("id");
		String[] columns = map.keySet().toArray(new String[map.size()]);
		String values = "VALUES(";
		for (int i = 0; i < columns.length; i++) {
			sql += columns[i];
			values += "?";
			if (i < columns.length - 1) {
				sql += ",";
				values += ",";
			}
		}
		sql += ")  ";
		values += ")";
		sql += values;
		if (showsql) {
			log.info(sql);
		}
		try {
			PreparedStatement ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			for(int i=0;i<columns.length;i++){
				ps.setObject(i+1,map.get(columns[i]));
			}
			return ps;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}
	public PreparedStatement mapToStatementForDelete(Map<String,Object> map,Connection conn){
		String sql = (String) map.get(DBBeanProcessor.ACTION) + " FROM "
		+ map.get(DBBeanProcessor.TABLE).toString() + " "+SQL.WHERE +" id="+map.get("id").toString();
		if (showsql) {
			log.info(sql);
		}
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			return ps;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
