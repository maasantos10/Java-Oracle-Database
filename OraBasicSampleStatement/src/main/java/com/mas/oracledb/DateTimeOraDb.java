package com.mas.oracledb;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
import com.mas.oracledb.service.EmployeeDateServiceImpl;
import com.mas.oracledb.util.CustomMessageType;

import oracle.jdbc.OracleType;
import oracle.jdbc.pool.OracleDataSource;

/**
 * Author: MAS - Marcos Almeida Santos
 * 
 * Purpose: Put forward how we can work with date, date and time, time stamp and local timezone within DB Oracle. 
 */

public class DateTimeOraDb {

	  static CustomMessageType cMessageType = new CustomMessageType("DateTimeOraDb");
	  OracleDbController oraControl = new OracleDbController();
	  OracleConfiguration oraDbConfiguration = new OracleConfiguration();

	  
	  public static void main(String[] args) throws Exception {

	    DateTimeOraDb demo = new DateTimeOraDb();
	    demo.run();
	  }
	  

	  void run() throws SQLException {
		  
		  EmployeeDateServiceImpl employeeDateServiceImpl = new EmployeeDateServiceImpl();
	    
		  try (Connection conn = oraDbConfiguration.getConnection()) {

	      // Truncate the existing table
		  employeeDateServiceImpl.truncateTable(conn);

	      // EmployeeDatedetails
	      int empId = 1001;
	      
	      Date dateOfBirth = Date.valueOf("1988-09-04");
	      LocalDateTime joiningDate = LocalDateTime.now();
	      ZonedDateTime dateOfResignation = ZonedDateTime.parse("2018-05-09T22:22:22-08:00[PST8PDT]");
	      Timestamp dateOfLeaving = Timestamp.valueOf(LocalDateTime.now());
	      
		  EmployeeDate employeesDate = new EmployeeDate();
    	  employeesDate.setId(empId);
    	  employeesDate.setDateOfBirth(dateOfBirth); 
    	  employeesDate.setJoiningDate(joiningDate); 
    	  employeesDate.setDateOfResignation(dateOfResignation);
    	  employeesDate.setDateOfLeaving(dateOfLeaving);
	      
	      cMessageType.show("\nInsert EmployeeDate record into table with id = "+empId);
	      employeeDateServiceImpl.insertEmployee(employeesDate, conn);
	      
	      cMessageType.show("\nEmployeeDatedetails of EmployeeDate = " + empId);
	      EmployeeDate emp =  employeeDateServiceImpl.getEmployeeDetails(1001, conn);
	      
	      ArrayList<EmployeeDate> listEmployeeDate = new ArrayList<EmployeeDate>();
	      
	      if (emp != null){
	    	  listEmployeeDate.add(emp);
	    	  cMessageType.printEmployeesDate(listEmployeeDate, "small");
	      }
	      
	      cMessageType.show("Update the EmployeeDatedetails of EmployeeDate= " + empId);
	      employeeDateServiceImpl.updateEmployee(empId, conn);
	      
	      cMessageType.show("\nUpdated details of EmployeeDate= " + empId);
	      EmployeeDate emp1 =  employeeDateServiceImpl.getEmployeeDetails(1001, conn);
	      
	      if (emp1 != null) {
	    	listEmployeeDate.add(emp1);
    	  	cMessageType.printEmployeesDate(listEmployeeDate, "small");
	      }

	      cMessageType.show("JDBCDateTimeSample demo completes.");
	    }

	  }


}
