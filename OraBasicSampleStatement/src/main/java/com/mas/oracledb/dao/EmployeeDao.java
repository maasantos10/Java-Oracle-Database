package com.mas.oracledb.dao;

/**
 * Author: MAS - Marcos Almeida Santos
 * 
 * Description: Class of Data Access Object of oracle table
 */

public interface EmployeeDao {

	/**
	 * Selects all the rows from the employee table.
	 * @param connection
	 */
	public void selectAll();
		
	/**
	* Gets employee details from the user and insert into
	* the Employee table.
	* @param connection
	*/
	public void insert();
	
	/**
	 * Gets the employee id from the user and retrieve the specific
	 * employee details from the employee table.
	 * @param connection
	 */
	public void selectOne();
	
	/**
	* Gets the employee id from the user and deletes the employee
	* row from the employee table.
	* @param connection
	*/
	public void delete();
	
	/**
	* Gets employee details from the user and update row in
	* the Employee table with the new details.
	* @param connection
	*/
	public void update();
	
	
}
