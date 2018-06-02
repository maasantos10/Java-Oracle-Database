package com.mas.oracledb;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import oracle.jdbc.pool.OracleDataSource;
import oracle.jdbc.OracleConnection;
import java.sql.DatabaseMetaData;

/**
 * Author: MAS - Marcos Almeida Santos
 * 
 * Purpose: Describe how to connect with database Oracle by DataSourceProperties.
 */

public class DataSourceProperties {

	final static String DB_URL= "jdbc:oracle:thin:@//localhost:1521/pdb2";
	final static String DB_USER = "hr";
	final static String DB_PASSWORD = "hr";

	 /*
	  * The method gets a database connection using 
	  * oracle.jdbc.pool.OracleDataSource. It also sets some connection 
	  * level properties, such as,
	  * OracleConnection.CONNECTION_PROPERTY_DEFAULT_ROW_PREFETCH,
	  * OracleConnection.CONNECTION_PROPERTY_THIN_NET_CHECKSUM_TYPES, etc.,
	  * There are many other connection related properties. Refer to 
	  * the OracleConnection interface to find more. 
	  */
	  public static void main(String args[]) throws SQLException {
		  Properties info = new Properties();     
		  info.put(OracleConnection.CONNECTION_PROPERTY_USER_NAME, DB_USER);
		  info.put(OracleConnection.CONNECTION_PROPERTY_PASSWORD, DB_PASSWORD);          
		  info.put(OracleConnection.CONNECTION_PROPERTY_DEFAULT_ROW_PREFETCH, "20");    
		  info.put(OracleConnection.CONNECTION_PROPERTY_THIN_NET_CHECKSUM_TYPES, 
		        "(MD5,SHA1,SHA256,SHA384,SHA512)");
		  info.put(OracleConnection.CONNECTION_PROPERTY_THIN_NET_CHECKSUM_LEVEL,
		        "REQUIRED");
		  OracleDataSource ods = new OracleDataSource();
		  ods.setURL(DB_URL);    
		  ods.setConnectionProperties(info);
	
		  // With AutoCloseable, the connection is closed automatically.
		  try (OracleConnection connection = (OracleConnection) ods.getConnection()) {
			 
			  // Get the JDBC driver name and version 
			  DatabaseMetaData dbmd = connection.getMetaData();       
			  System.out.println("Driver Name: " + dbmd.getDriverName());
			  System.out.println("Driver Version: " + dbmd.getDriverVersion());
			  
			  // Print some connection properties
			  System.out.println("Default Row Prefetch Value is: " + connection.getDefaultRowPrefetch());
			  System.out.println("Database Username is: " + connection.getUserName());
			  System.out.println();
			  
			  // Perform a database operation 
			  printEmployees(connection);
		  }   
	  }
	 /*
	  * Displays first_name and last_name from the employees table.
	  */
	  public static void printEmployees(Connection connection) throws SQLException {
		  
		  // Statement and ResultSet are AutoCloseable and closed automatically. 
		  try (Statement statement = connection.createStatement()) {      
			  try (ResultSet resultSet = 
					  statement.executeQuery("select first_name, last_name from employees")) {
				  System.out.println("FIRST_NAME" + "  " + "LAST_NAME");
				  System.out.println("---------------------");
				  while (resultSet.next())
					  System.out.println(resultSet.getString(1) + " " + 
				                         resultSet.getString(2) + " ");       
			  	  }
		  }
	  }
}
