package com.mas.oracledb.configuration;

import java.sql.SQLException;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;
import java.util.Properties;

import org.springframework.core.env.Environment;
/**
 * Author: MAS - Marcos Almeida Santos
 * 
 * Description: Simple sample of CRUD operation using the Oracle Statement object.
 */

public class OracleConfiguration {

	  private static final String DEFAULT_USER = "usr_jdbc";
	  private static final String DEFAULT_PWD = "usr_jdbc";
	  private static final String DEFAULT_URL = "jdbc:oracle:thin:@//localhost:1521/pdb2";
	  
	  /**
	   * Creates an OracleConnection instance and return it.
	   * @return oracleConnection
	   * @throws SQLException
	   */
	  /*
	  public OracleConnection getConnection() throws SQLException {
	    OracleDataSource ods = new OracleDataSource();
	    ods.setUser(env.getProperty("ora.user"));
	    ods.setPassword(env.getProperty("ora.password"));
	    ods.setURL(env.getProperty("ora.url"));
	    return (OracleConnection)ods.getConnection();
	  }
	  */

	  public OracleConnection getConnection() throws SQLException {
	  	
	  	Properties info = new Properties();     
	    info.put(OracleConnection.CONNECTION_PROPERTY_USER_NAME, DEFAULT_USER);
	    info.put(OracleConnection.CONNECTION_PROPERTY_PASSWORD, DEFAULT_PWD);          
	    info.put(OracleConnection.CONNECTION_PROPERTY_DEFAULT_ROW_PREFETCH, "20");    
	    info.put(OracleConnection.CONNECTION_PROPERTY_THIN_NET_CHECKSUM_TYPES, 
	          "(MD5,SHA1,SHA256,SHA384,SHA512)");
	    info.put(OracleConnection.CONNECTION_PROPERTY_THIN_NET_CHECKSUM_LEVEL,
	          "REQUIRED");
	    OracleDataSource ods = new OracleDataSource();
	    ods.setURL(DEFAULT_URL);    
	    ods.setConnectionProperties(info);
	    return (OracleConnection)ods.getConnection();
	   }

}
