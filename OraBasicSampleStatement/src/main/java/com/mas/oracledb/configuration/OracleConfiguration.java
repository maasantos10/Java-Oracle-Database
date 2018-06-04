package com.mas.oracledb.configuration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;
import java.util.Properties;

import org.springframework.core.env.Environment;

import com.mas.oracledb.controller.OraDbPlSqlController;
import com.mas.oracledb.util.CustomMessageType;
/**
 * Author: MAS - Marcos Almeida Santos
 * 
 * Description: Simple sample of CRUD operation using the Oracle Statement object.
 * Attention: You need to change the information in DEFAULT_USER, DEFAULT_PWD, DEFAULT_URL and 
 * DB_URL for your configuration Database Oracle. I recommend that you use Oracle R12C. 
 *  
 */

public class OracleConfiguration {

	  private static final String DEFAULT_USER = "usr_jdbc";
	  private static final String DEFAULT_PWD = "usr_jdbc";
	  private static final String DEFAULT_URL = "jdbc:oracle:thin:@//localhost:1521/pdb2";
	  private final static String DB_URL= "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(HOST=localhost)(PORT=1521)(PROTOCOL=tcp))(CONNECT_DATA=(SERVICE_NAME=pdb2)))";
	  private final static String CONN_FACTORY_CLASS = "oracle.jdbc.pool.OracleDataSource";
	  
	  public static final Logger LOGGER = LoggerFactory.getLogger(OracleConfiguration.class);
	 
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

	  /*
	  * Gets a database connection using a proxy user.
	  */
	  public static OracleConnection getConnection(String user, String password, 
			  OracleDataSource ods) throws SQLException {

		if (!user.isEmpty() && !password.isEmpty() ) {
			Properties info = new Properties();
			info.put(OracleConnection.CONNECTION_PROPERTY_USER_NAME, user);
			info.put(OracleConnection.CONNECTION_PROPERTY_PASSWORD, password);  
			info.put(OracleConnection.CONNECTION_PROPERTY_DEFAULT_ROW_PREFETCH, "20");
			info.put(OracleConnection.CONNECTION_PROPERTY_THIN_NET_CHECKSUM_TYPES, 
	          "(MD5,SHA1,SHA256,SHA384,SHA512)");
			info.put(OracleConnection.CONNECTION_PROPERTY_THIN_NET_CHECKSUM_LEVEL,
	          "REQUIRED");

			ods.setURL(DB_URL);    
			ods.setConnectionProperties(info);

		} else {
			LOGGER.info("Oracle - getConnection() - Password or User is empty");
		}

	    return (OracleConnection)ods.getConnection();
	  }
	  
	  public static void doSql(Connection conn, String sql) throws SQLException {
		  
		  try (Statement stmt = conn.createStatement()) {
			  stmt.execute(sql);
		  } catch (Exception ex) {
		    	LOGGER.info("Oracle - doSql():" + ex.getMessage());
		  }
	  }

	  public static void trySql(Connection conn, String sql) {
		  try {
		      doSql(conn, sql);
		  } catch (SQLException ex) {
			  LOGGER.info("Oracle - trySql():" + ex.getMessage());
		  }
	  }
	  
	  public void loadTable(Connection conn, String TABLE_NAME) throws SQLException {
		  
		  OraDbPlSqlController oraPlSqlContr = new OraDbPlSqlController(); 
		  
		    String insertDml = "INSERT INTO "+TABLE_NAME+" (NUM, NAME, INSERTEDBY) VALUES (?,?,?)";    
		    try (PreparedStatement prepStmt = conn.prepareStatement(insertDml)) {
		      prepStmt.setInt(1, 1);
		      prepStmt.setString(2, "ONE");
		      prepStmt.setString(3, "default");
		      prepStmt.addBatch();
		      
		      prepStmt.setInt(1, 2);
		      prepStmt.setString(2, "TWO");
		      prepStmt.setString(3, "default");
		      prepStmt.addBatch();
		      
		      prepStmt.setInt(1, 3);
		      prepStmt.setString(2, "THREE");
		      prepStmt.setString(3, "default");
		      prepStmt.addBatch();
		      
		      prepStmt.setInt(1, 4);
		      prepStmt.setString(2, "FOUR");
		      prepStmt.setString(3, "default");
		      prepStmt.addBatch();
		      
		      prepStmt.executeBatch();
		    }
		    
		    // Display initial set of rows loaded into the table.
		    LOGGER.info("Table '"+TABLE_NAME+"' is loaded with: ");
		    oraPlSqlContr.displayRows(conn, "default", TABLE_NAME);
		  }
		  
		  public void truncateTable(Connection conn, String TABLE_NAME) {
		    String sql = "TRUNCATE TABLE " + TABLE_NAME;
		    LOGGER.info(sql);
		    trySql(conn, sql);
		  }
	  
	  
}
