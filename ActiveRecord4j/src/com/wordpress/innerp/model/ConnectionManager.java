package com.wordpress.innerp.model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;



public class ConnectionManager {
	
	private static ThreadLocal<Connection> conns = new ThreadLocal<Connection>();
	private static DataSource dataSource;
	private static Logger log = Logger.getLogger("ConnectionManager");
	private static String prefix;
	static{
		init(null);
	}
	private static  void init(Properties dbProperties){
		if(dbProperties == null){
			dbProperties = new Properties();
			try {
				dbProperties.load(ConnectionManager.class.getResourceAsStream("dbconfig.properties"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			prefix = (String) dbProperties.get("prefix");
			dataSource = DruidDataSourceFactory.createDataSource(dbProperties);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static Connection getConnection(){
		Connection conn = conns.get();
		try {
			if(conn==null||conn.isClosed()){
				conn = dataSource.getConnection();
				conns.set(conn);
			}
			return conn;
		} catch (SQLException e) {
		    e.printStackTrace();
		}
		return null;
	}
	public static void closeConnection(){
		Connection conn = conns.get();
		try {
			if(conn!=null&&!conn.isClosed()){
				conn.setAutoCommit(true);
	            conn.close();
	            
			}else{
				log.warning("connection 不存在或者已将关闭，不必关闭");
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	public static void closeDataSource(){
		
		
	}
	public static void closeResultSet(ResultSet rs){
		try {
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static void closeStatement(Statement s){
		try {
			s.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static String getPreFix(){
		return prefix;
	}

	
	

}
