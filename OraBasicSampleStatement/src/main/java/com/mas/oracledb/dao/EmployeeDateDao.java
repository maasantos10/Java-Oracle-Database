package com.mas.oracledb.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mas.oracledb.model.EmployeeDate;

import oracle.jdbc.OracleConnection;

/**
 * Author: MAS - Marcos Almeida Santos
 * 
 * Description: Class of Data Access Object of oracle table
 */

public interface EmployeeDateDao {

	public void truncateTable(Connection conn)  throws SQLException ;
	
	public void updateEmployee(int id, Connection conn) throws SQLException;
	
	public EmployeeDate getEmployeeDetails(int id, Connection conn) throws SQLException;
	
	public void insertEmployee(EmployeeDate emp, Connection conn) throws SQLException;
	
	
	
	
}
