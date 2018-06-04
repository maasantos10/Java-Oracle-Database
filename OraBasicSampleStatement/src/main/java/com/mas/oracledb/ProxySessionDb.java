package com.mas.oracledb;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mas.oracledb.configuration.OracleConfiguration;
import com.mas.oracledb.controller.OracleDbProxyController;
import com.mas.oracledb.util.CustomMessageType;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;

/***
 * 
 * @author MAS - Marcos Almeida Santos
 * 
 * Purpose: The code sample shows how to connect to an Oracle Database using Proxy Session
 * 
 */

public class ProxySessionDb {
	
	private static CustomMessageType cMessageType = new CustomMessageType("ProxySessionDb");
	
	public static void main(String args[]) throws SQLException {   
	    OracleDataSource ods = new OracleDataSource();
	    OracleConfiguration oraConfig = new OracleConfiguration();
	    OracleConnection proxyConn = oraConfig.getConnection("proxy", "proxy", ods);
	    OracleDbProxyController OraProxyContr = new OracleDbProxyController();
	    
	    // isProxySession is false before opening a proxy session
	    cMessageType.show("Before a proxy session is open, isProxySession: "
	        + proxyConn.isProxySession());

	    // check if the user is "proxy"
	    OraProxyContr.checkUser(proxyConn);
	    
	    // open a proxy session for the user "jeff".
	    // This session reuses existing proxy session to connect as user, "jeff". 
	    // There is no need to authenticate the user "jeff". 
	    OraProxyContr.demoProxySession(proxyConn, "jeff");   
	    
	    // open a proxy session for the user "smith".
	    // This session reuses existing proxy session to connect as user "smith" 
	    // There is no need to authenticate the user "smith". 
	    OraProxyContr.demoProxySession(proxyConn, "smith");
	    
	    // Close the proxy connection
	    proxyConn.close();    
	  }
	
}
