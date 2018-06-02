package com.mas.oracledb.service;

public interface EmployeeService {

	/**
	 * Selects all the rows from the employee table.
	 * @param none
	 */
	public void selectAll();
		
	/**
	* Gets employee details from the user and insert into
	* the Employee table.
	* @param none
	*/
	public void insert();
	
	/**
	 * Gets the employee id from the user and retrieve the specific
	 * employee details from the employee table.
	 * @param none
	 */
	public void selectOne();
	
	/**
	* Gets the employee id from the user and deletes the employee
	* row from the employee table.
	* @param none
	*/
	public void delete();
	
	/**
	* Gets employee details from the user and update row in
	* the Employee table with the new details.
	* @param none
	*/
	public void update();
}
