package com.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.exceptions.ConnectionErrorException;
import com.exceptions.QueryExecutionException;

public class DBConnection {
	private static Connection connection;
	
	public static Connection getConnection() throws ConnectionErrorException, SQLException {
		if(connection == null ){
			connection = createConnection();
			return connection;
		} else {
			if(connection.isClosed()) {
				connection = createConnection();
				return connection;
			} else {
				return connection;
			}
			
		}
	}
	public static Connection getConnection_old() throws ConnectionErrorException{
		if(connection == null ){
			connection = createConnection();
			return connection;
		} else {
			return connection;
		}
	}
	public static Connection createConnection() throws ConnectionErrorException{
		Connection connection = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbclass","root", "toor");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Connection Error");
			throw new ConnectionErrorException();
		}
		return connection;
	}
	public static void closeConnection(Connection connection) throws ConnectionErrorException {
		
		try{
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error While closing Connection");
			throw new ConnectionErrorException();
		}
		
	}
	
	public static ResultSet executeSQLQuery(Connection conn, String query) throws QueryExecutionException  {
		ResultSet rs = null;
		try{
			Statement statement = conn.createStatement();
			rs = statement.executeQuery(query);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error While Executing the Query");
			throw new QueryExecutionException(); 
		}
		return rs;
	}
	
	public static void udateSQLQuery(Connection conn, String query) throws QueryExecutionException {
		try{
			Statement statement = conn.createStatement();
			int xyz = statement.executeUpdate(query);
			System.out.println(xyz + " row(s) modified");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Updating DB");
			throw new QueryExecutionException(); 
		}
	}
	
}
