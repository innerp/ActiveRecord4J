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
        StringBuilder sql = new StringBuilder();
        sql.append((String) map.get(DBBeanProcessor.ACTION));
        sql.append(SQL.BLANK);
        sql.append(map.get(DBBeanProcessor.TABLE).toString());
        sql.append(SQL.BLANK);
        sql.append("SET");
        sql.append(SQL.BLANK);
		map.remove(DBBeanProcessor.TABLE);
		map.remove(DBBeanProcessor.ACTION);
		long id = (Long) map.get("id");
		map.remove("id");
		String[] keys = map.keySet().toArray(new String[map.size()]);
		for (int i = 0; i < keys.length; i++) {
			sql.append(keys[i]);
			sql.append("=? ");
			if (i < keys.length - 1) {
				sql.append(",");
			}
		}
		sql.append(" WHERE id=");
		sql.append(id);
		if (showsql) {
			log.info(sql.toString());
		}
		try {
			PreparedStatement ps = conn.prepareStatement(sql.toString(),Statement.RETURN_GENERATED_KEYS);
			for(int i=0;i<keys.length;i++){
				ps.setObject(i+1,map.get(keys[i]));
			}
			sql = null;
			return ps;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

	public PreparedStatement mapToStatementForInsert(Map<String, Object> map,
			Connection conn) {
		StringBuilder sql = new StringBuilder();
		sql.append((String) map.get(DBBeanProcessor.ACTION));
		sql.append(" INTO ");
		sql.append(map.get(DBBeanProcessor.TABLE).toString());
		sql.append("(");
		map.remove(DBBeanProcessor.ACTION);
		map.remove(DBBeanProcessor.TABLE);
		map.remove("id");
		String[] columns = map.keySet().toArray(new String[map.size()]);
		StringBuilder values = new StringBuilder();
		values.append("VALUES(");
		for (int i = 0; i < columns.length; i++) {
			sql.append(columns[i]);
			values.append("?");
			if (i < columns.length - 1) {
				sql.append(",");
				values.append(",");
			}
		}
		sql.append(")  ");
		values.append(")");
		sql.append(values.toString());
		if (showsql) {
			log.info(sql.toString());
		}
		try {
			PreparedStatement ps = conn.prepareStatement(sql.toString(),Statement.RETURN_GENERATED_KEYS);
			for(int i=0;i<columns.length;i++){
				ps.setObject(i+1,map.get(columns[i]));
			}
			values = null;
			sql = null;
			return ps;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}
	public PreparedStatement mapToStatementForDelete(Map<String,Object> map,Connection conn){
		StringBuilder sql = new StringBuilder();
		sql.append((String) map.get(DBBeanProcessor.ACTION));
		sql.append(" FROM ");
		sql.append(map.get(DBBeanProcessor.TABLE).toString());
		sql.append(SQL.BLANK);
		sql.append(SQL.WHERE );
		sql.append(" id=?");
		if (showsql) {
			log.info(sql.toString());
		}
		try {
			PreparedStatement ps = conn.prepareStatement(sql.toString());
			ps.setLong(1,(Long)map.get("id"));
			sql = null;
			return ps;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
