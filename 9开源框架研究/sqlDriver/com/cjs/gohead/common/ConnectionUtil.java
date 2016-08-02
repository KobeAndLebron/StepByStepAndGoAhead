package com.cjs.gohead.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.mysql.cj.jdbc.Driver;

public class ConnectionUtil {
	public static final String URL = "jdbc:mysql://192.168.130.14:3308/default/";
	public static final String USERNAME = "root";
	public static final String USERPWD = "passw0rd";
	
	static{
		// register table
		try {
			Driver d = new Driver();\
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnectoin(){
		Connection c = null;
		try {
			c = DriverManager.getConnection(URL, USERNAME, USERPWD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return c;
	}
	
	public static boolean update(String sql){
		Connection c = getConnectoin();
		Statement statement;
		try {
			statement = c.createStatement();
			statement.execute(sql);
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public static void main(String[] args) {
		String sql = "drop table if exists test_zp_1466649386877";
		ConnectionUtil.update(sql);
	}
}
