package com.mas.oracledb.service;

import com.mas.oracledb.dao.EmployeeDao;
import com.mas.oracledb.dao.EmployeeDaoImpl;

public class EmployeeServiceImpl implements EmployeeService {

	EmployeeDaoImpl employeeDaoImpl = new EmployeeDaoImpl();

	@Override
	public void selectAll() {
		employeeDaoImpl.selectAll();
	}

	@Override
	public void insert() {
		employeeDaoImpl.insert();
	}

	@Override
	public void selectOne() {
		employeeDaoImpl.selectOne();
	}

	@Override
	public void delete() {
		employeeDaoImpl.delete();
	}

	@Override
	public void update() {
		employeeDaoImpl.update();
	}
	
}
