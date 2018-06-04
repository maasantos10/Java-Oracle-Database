package com.mas.oracledb.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.mas.oracledb.configuration.OracleConfiguration;
import com.mas.oracledb.util.CustomMessageType;

import oracle.jdbc.pool.OracleDataSource;

/**
 * Author: MAS - Marcos Almeida Santos
 * 
 * Purpose: Put forward how we can work with json and DB Oracle.
 */

public class OracleDbJsonController {

	private static CustomMessageType cMessageType = new CustomMessageType("OracleDbJsonController");
	private OracleConfiguration oraDbConfiguration = new OracleConfiguration();
	
	private PreparedStatement getSalaryStatement = null;
	private Statement statement = null;
	private Connection connection = null;
	
	private void SetUp() throws SQLException {
		
	    connection = oraDbConfiguration.getConnection();
	    statement = connection.createStatement();

	    // PreparedStatement to return the salary from an employee using the
	    // employee number (with Path Notation Query over the document).
	    getSalaryStatement = connection.prepareStatement(
	        "SELECT JSON_VALUE(employee_document, '$.salary') "
	        + "FROM JSON_EMP_JDBC_SAMPLE where JSON_VALUE "
	        + "(employee_document, '$.employee_number') = ?");
	}

	private double getSalaryEmp(long employeeNumber)  {
		
		double empNumber = 0;
		try {
	    // Bind parameter (employee number) to the query.
	    getSalaryStatement.setLong(1, employeeNumber);

	    // Return salary (negative value if not found).
	    ResultSet employeesRs = getSalaryStatement.executeQuery();
	    if (employeesRs.next()) {
	    	empNumber = employeesRs.getDouble(1);
	    } else {
	    	empNumber=  -1d;
	    }
		} catch (SQLException sqlException) {
			cMessageType.showError("Error!. SQLException was expected to be thrown because of bad formatted JSON.", sqlException);
		}
		return empNumber;
	}
	  
	private ResultSet Execute(String sql) throws SQLException {
		return statement.executeQuery(sql);
	}

	private void ExecuteAndShow(String sql) throws SQLException {

		ResultSet resultSet = Execute(sql);
	    final int columnCount = resultSet.getMetaData().getColumnCount();
	    while (resultSet.next()) {
	      StringBuffer output = new StringBuffer();
	      for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++)
	        output.append(resultSet.getString(columnIndex)).append("|");
	      cMessageType.show(output.toString());
	    }
	  }
	  
	private void TearDown() throws SQLException {
		statement.close();
		getSalaryStatement.close();
		connection.close();
	}
	  
	public void ExecuteJsonRoutines() throws Exception {

		// Set up test: open connection/statement to be used through the demo.
		SetUp();

		// Test the constraint with an incorrect JSON document.
		// If the SQLException is not caught, show as an error.
		try {
		      Execute("INSERT INTO JSON_EMP_JDBC_SAMPLE VALUES (SYS_GUID(), SYSTIMESTAMP, '{\"emp\"loyee_number\": 5, \"employee_name\": \"Jack Johnson\"}')");
		      cMessageType.showError("Error!. SQLException was expected to be thrown because of bad formatted JSON.", new SQLException());
		    } catch (SQLException sqlException) {
		    	cMessageType.show("Good catch! SQLException was expected to be thrown because of bad formatted JSON.");
		    }

	    // This is a Simple Dot Notation Query on a column with a JSON document.
	    // The return value for a dot-notation query is always a string (data type VARCHAR2) representing JSON data.
	    // The content of the string depends on the targeted JSON data, as follows:
	    // If a single JSON value is targeted, then that value is the string content, whether it is a JSON scalar, object, or array.
	    // If multiple JSON values are targeted, then the string content is a JSON array whose elements are those values.
	    ExecuteAndShow("SELECT em.employee_document.employee_number, em.employee_document.salary FROM JSON_EMP_JDBC_SAMPLE em");

	    // This is a Simple Path Notation Query
	    // SQL/JSON path expressions are matched by SQL/JSON functions and conditions against JSON data, to select portions of it.
	    // Path expressions are analogous to XQuery and XPath expression. They can use wild-cards and array ranges. Matching is case-sensitive.
	    ExecuteAndShow("SELECT JSON_VALUE(employee_document, '$.employee_number') FROM JSON_EMP_JDBC_SAMPLE where JSON_VALUE(employee_document, '$.salary') > 2000");

	    // This is a Complex Path Notation Query (employees with at least one son named 'Angie').
	    // An absolute simple path expression begins with a dollar sign ($), which represents the path-expression context item.
	    // The dollar sign is followed by zero or more path steps.
	    // Each step can be an object step or an array step, depending on whether the context item represents a JSON object or a JSON array.
	    // The last step of a simple path expression can be a single, optional function step.
	    // In all cases, path-expression matching attempts to match each step of the path expression, in turn.
	    // If matching any step fails then no attempt is made to match the subsequent steps, and matching of the path expression fails.
	    // If matching each step succeeds then matching of the path expression succeeds.
	    ExecuteAndShow("SELECT JSON_VALUE(employee_document, '$.employee_name') FROM JSON_EMP_JDBC_SAMPLE where JSON_EXISTS(employee_document, '$.sons[*]?(@.name == \"Angie\")')");

	    // Demo using getSalary for an existing number
	    cMessageType.show("Get salary for Jane Doe (employee number:2), "
	        + "2010 expected: " + getSalaryEmp(2));

	    // Demo using getSalary for a non existing number
	    cMessageType.show("Get salary for non existing (employee number:5), "
	        + "negative value expected: " + getSalaryEmp(5));

	    // Tear down test: close connections/statements that were used through the demo.
	    TearDown();
	}
	 
	
}
