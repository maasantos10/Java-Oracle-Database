package com.mas.oracledb.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.mas.oracledb.model.EmployeeDate;

/**
 * Author: MAS - Marcos Almeida Santos
 * 
 * Description: Class of Employee Date Service.
 */

public interface EmployeeDateService {

	public void truncateTable(Connection conn)  throws SQLException ;
	
	public void updateEmployee(int id, Connection conn) throws SQLException;
	
	public EmployeeDate getEmployeeDetails(int id, Connection conn) throws SQLException;
	
	public void insertEmployee(EmployeeDate emp, Connection conn) throws SQLException;
	
	
}
