package com.mas.oracledb.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLType;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import com.mas.oracledb.configuration.OracleConfiguration;
import com.mas.oracledb.controller.OracleDbController;
import com.mas.oracledb.model.Employee;
import com.mas.oracledb.model.EmployeeDate;
import com.mas.oracledb.util.CustomMessageType;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleType;

/**
 * Author: MAS - Marcos Almeida Santos
 * 
 * Description: implementation of Class of Data Access Object of oracle table
 */

public class EmployeeDateDaoImpl implements EmployeeDateDao {

	
	  
	  static CustomMessageType cMessageType = new CustomMessageType("EmployeeDaoImpl");
	  
	  OracleDbController oraControl = new OracleDbController();
	  OracleConfiguration oraDbConfiguration = new OracleConfiguration();
	  
	  /**
	   * Inserts EmployeeDatedata into table using given connection.
	   *
	   * @param emp
	   *          EmployeeDatedata
	   * @param conn
	   *          Connection to be used to insert the EmployeeDatedata.
	   * @throws SQLException
	   */
	  public void insertEmployee(EmployeeDate emp, Connection conn) throws SQLException {
	    
		  final String insertQuery = "INSERT INTO EMP_DATE_JDBC_SAMPLE VALUES(?,?,?,?,?)";
		  try (PreparedStatement pstmt = conn.prepareStatement(insertQuery)) {
			  SQLType dataType = null;

			  pstmt.setInt(1, emp.getId());
			  pstmt.setDate(2, emp.getDateOfBirth());
			  dataType = OracleType.TIMESTAMP_WITH_LOCAL_TIME_ZONE;
			  pstmt.setObject(3, emp.getJoiningDate(), dataType);
			  dataType = OracleType.TIMESTAMP_WITH_TIME_ZONE;
			  pstmt.setObject(4, emp.getResignationDate(), dataType);
			  pstmt.setTimestamp(5, emp.getDateOfLeaving());
			  pstmt.executeUpdate();
			  cMessageType.show("EmployeeDaterecord inserted successfully.");
		  }
	  }

	  /**
	   * Fetches the EmployeeDatedata for given EmployeeDateid.
	   *
	   * @param id
	   *          EmployeeDateid.
	   * @param conn
	   *          Connection to be used to fetch EmployeeDatedata.
	   * @return
	   * @throws SQLException
	   */
	  public EmployeeDate getEmployeeDetails(int id, Connection conn)
	      throws SQLException {
	    final String selectQuery = "SELECT EMP_ID, DATE_OF_BIRTH, DATE_OF_JOINING, "
	        + "DATE_OF_RESIGNATION, DATE_OF_LEAVING FROM EMP_DATE_JDBC_SAMPLE WHERE EMP_ID = ?";
	    try (PreparedStatement pstmt = conn.prepareStatement(selectQuery)) {
	      pstmt.setInt(1, id);
	      try (ResultSet rs = pstmt.executeQuery()) {
	        if (rs.next()) {
	        	int employeeId = rs.getInt(1);
	        	Date datOfBirth = rs.getDate(2);
	        	LocalDateTime dateOfJoining = rs.getObject(3, LocalDateTime.class);
	        	ZonedDateTime dateOfResignation = rs.getObject(4, ZonedDateTime.class);
	        	Timestamp dateOfLeaving = rs.getTimestamp(5);
	          
	        	EmployeeDate employeesDate = new EmployeeDate();
	        	employeesDate.setId(employeeId);
	        	employeesDate.setDateOfBirth(datOfBirth); 
	        	employeesDate.setJoiningDate(dateOfJoining); 
	        	employeesDate.setDateOfResignation(dateOfResignation);
	        	employeesDate.setDateOfLeaving(dateOfLeaving);
	          
	        	return employeesDate;

	        } else {
	        	cMessageType.show("EmployeeDaterecord not found in the database.");
	          return null;
	        }
	      }

	    }
	  }

	  /**
	   * Updates the EmployeeDaterecord for given EmployeeDateid.
	   *
	   * @param id
	   *          EmployeeDateid.
	   * @param conn
	   *          Connection to be used to update EmployeeDatedata.
	   * @throws SQLException
	   */
	  public void updateEmployee(int id, Connection conn) throws SQLException {
	    final String updateQuery = "UPDATE EMP_DATE_JDBC_SAMPLE SET DATE_OF_JOINING=? WHERE EMP_ID =?";
	    try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
	      SQLType dataType = OracleType.TIMESTAMP_WITH_LOCAL_TIME_ZONE;
	      pstmt.setObject(1,
	          ZonedDateTime.parse("2015-12-09T22:22:22-08:00[PST8PDT]"), dataType);
	      pstmt.setInt(2, id);
	      int updateCount = pstmt.executeUpdate();
	      cMessageType.show("Successfully updated EmployeeDatedetails.");
	    }
	  }

	  public void truncateTable(Connection conn) throws SQLException {
	    final String sql = "TRUNCATE TABLE EMP_DATE_JDBC_SAMPLE";
	    try (Statement st = conn.createStatement()) {
	      st.executeQuery(sql);
	      cMessageType.show("Table truncated successfully.");
	    } catch (SQLException e) {
	    	cMessageType.showError("Truncate table operation failed.", e);
	    }
	  }
}
