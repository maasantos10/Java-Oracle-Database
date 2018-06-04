package com.mas.oracledb.controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import oracle.jdbc.OracleConnection;

public class OracleDbProxyController {

	/*
	  * Demonstrates the following: 
	  * (1) Start a Proxy Session: Starts the proxy Session with corresponding
	  * roles and authenticates the users "jeff" or "smith". 
	  * (2) Access Proxy user's table: The users "jeff" or "smith" can access 
	  * the "proxy" user table, 'proxy_account' through the proxy session. 
	  * (3) Close the Proxy Session: Close the proxy session for the user "jeff" 
	  * or "smith".
	  */
	  public static void demoProxySession(OracleConnection conn, String proxyUser)
	      throws SQLException {
		  
	    Properties prop = new Properties();
	    prop.put(OracleConnection.PROXY_USER_NAME, proxyUser);
	    
	    // corresponds to the alter sql statement (select, insert roles)
	    String[] roles = { "select_role", "insert_role" };
	    prop.put(OracleConnection.PROXY_ROLES, roles);
	    conn.openProxySession(OracleConnection.PROXYTYPE_USER_NAME, prop);
	    System.out.println("======= demoProxySession BEGIN =======");
	    System.out.println("After the proxy session is open, isProxySession: "
	        + conn.isProxySession());
	    // proxy session can act as users "jeff" & "smith" to access the 
	    // user "proxy" tables       
	    try (Statement stmt = conn.createStatement()) {
	      // Check who is the database user
	      checkUser(conn);
	      // play insert_role into proxy.proxy_account, go through
	      stmt.execute("insert into proxy.proxy_account values (1)");
	      System.out.println("insert into proxy.proxy_account, allowed");
	      // play select_role from proxy.proxy_account, go through          
	      try (ResultSet rset = stmt.executeQuery("select * from " 
	         + " proxy.proxy_account")) {
	      while (rset.next())  {
	        // display the execution results of a select query.  
	        System.out.println(rset.getString(1));
	      }
	      System.out.println("select * from proxy.proxy_account, allowed");
	      // play delete_role from proxy.proxy_account, SQLException
	      stmt.execute("delete from proxy.proxy_account where purchase=1");        
	    } catch(Exception e) {
	        System.out.println("delete from proxy.proxy_account, not allowed");
	    }
	    System.out.println("======= demoProxySession END =======");
	    // Close the proxy session of user "jeff" 
	    conn.close(OracleConnection.PROXY_SESSION);
	   }
	  }
	 
	 /*
	  * Checks the database user. Note that the user will be proxy.
	  */
	  public static void checkUser(Connection conn) throws SQLException {
	    
		  try (Statement stmt = conn.createStatement()) {
			  try (ResultSet rset = stmt.executeQuery("select user from dual")) {
				  while (rset.next()) {
					  System.out.println("User is: " + rset.getString(1));
				  }
			  }
		  }    
	  }

}
