package com.mas.oracledb.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.sql.Connection;
import java.sql.JDBCType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLXML;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import com.mas.oracledb.configuration.OracleConfiguration;
import com.mas.oracledb.util.CustomMessageType;

public class OracleDbXmlController {

	  private static CustomMessageType cMessageType = new CustomMessageType("OracleDbXmlController");
	  private OracleConfiguration oraDbConfiguration = new OracleConfiguration();
	
	/**
	   * Demonstrate the sample code.
	   * <ol>
	   * <li>drop the sample table to insure the table can be created properly</li>
	   * <li>create the sample table</li>
	   * <li>insert SQLXML values into the table</li>
	   * <li>read the SQLXML values from the table</li>
	   * <li>clean up</li>
	   * </ol>
	   *
	   * @throws Exception
	   */
	  public void ExecuteAllXmlSamples() throws Exception {
	    try (Connection conn = oraDbConfiguration.getConnection()) {
	      truncateTable(conn);
	      loadTable(conn);
	      queryTable(conn);
	      truncateTable(conn);
	    }
	  }

	  /**
	   * Clear the sample table with two columns.
	   *
	   * @param conn a database Connection
	   * @throws SQLException
	   */
	  public void truncateTable(Connection conn) throws SQLException {
	    String sql = "TRUNCATE TABLE SQLXML_JDBC_SAMPLE";
	    //cMessageType.show(sql);
	    OracleConfiguration.doSql(conn, sql);
	  }

	  /**
	   * Create SQLXML values and insert them into the sample table. Demonstrates
	   * two possible ways to create a SQLXML value. There are others. Uses the
	   * generic setObject(int, Object, SQLType) method to set the parameters.
	   *
	   * @param conn
	   * @throws SQLException
	   */
	  public void loadTable(Connection conn) throws SQLException {
	    String insertDml = "INSERT INTO SQLXML_JDBC_SAMPLE (DOCUMENT, ID) VALUES (?, ?)";
	    try (PreparedStatement prepStmt = conn.prepareStatement(insertDml)) {

	      SQLXML xml = conn.createSQLXML();
	      xml.setString("<?xml version=\"1.0\"?>\n" +
	                    "               <EMP>\n" +
	                    "                  <EMPNO>221</EMPNO>\n" +
	                    "                  <ENAME>John</ENAME>\n" +
	                    "               </EMP>");

	      prepStmt.setObject(1, xml, JDBCType.SQLXML);
	      prepStmt.setObject(2, 221, JDBCType.NUMERIC);
	      prepStmt.executeUpdate();

	      xml = conn.createSQLXML();
	      Writer w = xml.setCharacterStream();
	      w.write("<?xml version=\"1.0\"?>\n");
	      w.write("               <EMP>\n");
	      w.write("                  <EMPNO>222</EMPNO>\n");
	      w.write("                  <ENAME>Mary</ENAME>\n");
	      w.write("               </EMP>\n");
	      w.close();

	      prepStmt.setObject(1, xml, JDBCType.SQLXML);
	      prepStmt.setObject(2, 222, JDBCType.NUMERIC);
	      prepStmt.executeUpdate();

	    }
	    catch (IOException ex) {
	      cMessageType.showError("loadTable()", new SQLException(ex));
	    }
	  }

	  /**
	   * Query the sample table, retrive the SQLXML values and print their contents
	   * to stdout. Uses the generic getObject(int, Class) method.
	   *
	   * @param conn
	   * @throws SQLException
	   */
	  public void queryTable(Connection conn) throws SQLException {
	    String query = "SELECT DOCUMENT, ID FROM SQLXML_JDBC_SAMPLE ORDER BY ID";
	    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
	      ResultSet rs = pstmt.executeQuery();
	      while (rs.next()) {
	        SQLXML sqlxml = rs.getObject(1, SQLXML.class);
	        InputStream binaryStream = sqlxml.getBinaryStream();
	        DocumentBuilder parser
	                = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	        Document result = parser.parse(binaryStream);
	        cMessageType.printDocument(result, System.out);
	        cMessageType.show(" ");
	      }
	    }
	    catch (IOException | TransformerException | SAXException | ParserConfigurationException ex) {
	    	cMessageType.showError("queryTable()", new SQLException(ex));
	    }
	  }

}
