package com.mas.oracledb.service;

import java.sql.Connection;
import java.sql.SQLException;

import com.mas.oracledb.dao.EmployeeDao;
import com.mas.oracledb.dao.EmployeeDaoImpl;
import com.mas.oracledb.dao.EmployeeDateDaoImpl;
import com.mas.oracledb.model.EmployeeDate;

/**
 * Author: MAS - Marcos Almeida Santos
 * 
 * Description: Implementation of service.
 */


public class EmployeeDateServiceImpl implements EmployeeDateService {

	EmployeeDateDaoImpl employeeDateDaoImpl = new EmployeeDateDaoImpl();

	@Override
	public void truncateTable(Connection conn)  throws SQLException  {
		employeeDateDaoImpl.truncateTable(conn);
		
	}

	@Override
	public void updateEmployee(int id, Connection conn) throws SQLException {
		employeeDateDaoImpl.updateEmployee(id, conn);
		
	}

	@Override
	public EmployeeDate getEmployeeDetails(int id, Connection conn) throws SQLException {
		
		return employeeDateDaoImpl.getEmployeeDetails(id, conn);
	}

	@Override
	public void insertEmployee(EmployeeDate emp, Connection conn) throws SQLException {
		employeeDateDaoImpl.insertEmployee(emp, conn);
		
	}
	
		
}
