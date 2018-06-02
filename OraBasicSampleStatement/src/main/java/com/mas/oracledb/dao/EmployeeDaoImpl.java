package com.mas.oracledb.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mas.oracledb.configuration.OracleConfiguration;
import com.mas.oracledb.controller.OracleDbController;
import com.mas.oracledb.model.Employee;
import com.mas.oracledb.util.CustomMessageType;

import oracle.jdbc.OracleConnection;

/**
 * Author: MAS - Marcos Almeida Santos
 * 
 * Description: Implementation.
 */

public class EmployeeDaoImpl implements EmployeeDao {

	private static final String SQL_INSERT = "INSERT INTO EMP (EMPNO, ENAME, JOB, HIREDATE, SAL) VALUES (%d, '%s', '%s', TO_DATE('%s', 'yyyy/mm/dd'), %f)";

	  private static final String SQL_UPDATE
	     = "UPDATE EMP SET ENAME = '%s', JOB = '%s', HIREDATE = "
	         + "TO_DATE('%s', 'yyyy/mm/dd'), SAL = %f WHERE EMPNO = %d";

	  private static final String SQL_DELETE = "DELETE FROM EMP WHERE EMPNO = %d";
	  private static final String SQL_SELECT_ALL = "SELECT * FROM EMP";
	  private static final String SQL_SELECT_ONE = "SELECT * FROM EMP WHERE EMPNO = %d";
	  
	  static CustomMessageType cMessageType = new CustomMessageType("EmployeeDaoImpl");
	  
	  OracleDbController oraControl = new OracleDbController();
	  OracleConfiguration oraDbConfiguration = new OracleConfiguration();
	  
	  /**
	   * Gets employee details from the user and insert into
	   * the Employee table.
	   * @param connection
	   */
	  @Override
	  public void insert() {
		  
		  //OracleDbController oraControl = new OracleDbController();
		  //OracleConfiguration oraDbConfiguration = new OracleConfiguration();
		  
		  OracleConnection connection;
		  try {
				connection = oraDbConfiguration.getConnection();
				try(Statement stmt = connection.createStatement()) {
					Employee employee = oraControl.getEmployeeFromConsole();
					if(employee == null) {
						cMessageType.showError("Unable to get employee details.");
			    	  	return;
				  	}
				  	String insertSQL = String.format(SQL_INSERT, employee.getId(),
			        employee.getName(), employee.getDesignation(),
			        employee.getJoiningDate(), employee.getSalary());
				  	boolean status = stmt.execute(insertSQL);
				  	cMessageType.show("Insert successfull !!");
				  }
				  catch(SQLException sqle) {
					cMessageType.showError(sqle.getMessage());
					connection.close();
				  } finally {
					  connection.close();  
				  }
				
			  } catch (SQLException connE) {
				cMessageType.showError(connE.getMessage());
			  }
	  }

	  /**
	   * Gets employee details from the user and update row in
	   * the Employee table with the new details.
	   * @param connection
	   */
	  @Override
	  public void update() {
		  
		  OracleConnection connection;
		  try {
				connection = oraDbConfiguration.getConnection();

				try(Statement stmt = connection.createStatement()) {
					Employee employee = oraControl.getEmployeeFromConsole();
					if(employee == null) {
						cMessageType.showError("Unable to get employee details.");
						return;
					}
					String updateSQL = String.format(SQL_UPDATE,
							employee.getName(), employee.getDesignation(),
							employee.getJoiningDate(), employee.getSalary(),
							employee.getId());
					int noOfRecordsUpdated = stmt.executeUpdate(updateSQL);
					cMessageType.show("Number of records updated : " + noOfRecordsUpdated);
				} catch(SQLException sqle) {
					cMessageType.showError(sqle.getMessage());
					connection.close();
				} finally {
					connection.close();  
				}
		  } catch (SQLException connE) {
				cMessageType.showError(connE.getMessage());
		  }
	  }

	  /**
	   * Gets the employee id from the user and deletes the employee
	   * row from the employee table.
	   * @param connection
	   */
	  @Override
	  public void delete() {
		  
		  OracleConnection connection;
		  try {
				connection = oraDbConfiguration.getConnection();
		  
				try(Statement stmt = connection.createStatement()) {
					int employeeID = oraControl.getEmployeeIDFromConsole();
					String deleteSQL = String.format(SQL_DELETE,employeeID);
					int noOfRecordsDeleted = stmt.executeUpdate(deleteSQL);
					cMessageType.show("Number of records deleted : " + noOfRecordsDeleted);
				} catch(SQLException sqle) {
					cMessageType.showError(sqle.getMessage());
					connection.close();
				} finally {
					  connection.close();  
				}
			} catch (SQLException connE) {
					cMessageType.showError(connE.getMessage());
			}
	  }

	  /**
	   * Gets the employee id from the user and retrieve the specific
	   * employee details from the employee table.
	   * @param connection
	   */
	  
	  public void selectOne() {
	    
		  OracleConnection connection;
		  try {
				connection = oraDbConfiguration.getConnection();
		  
				int empId = oraControl.getEmployeeIDFromConsole();
	    
				 System.out.println("selectOne - Employee ID choosed:" + empId);
			    
				 ArrayList<Employee> listEmployee = new ArrayList<Employee>();
			    
				 try(Statement stmt = connection.createStatement()) {
					 final String selectSQL = String.format(SQL_SELECT_ONE, empId);
			      
					 ResultSet rs = stmt.executeQuery(selectSQL);
			      
					 if (rs.next()) {
			    	  
						 while (rs.next()) {
			    	  
							 Employee employees = new Employee();
				    	  
							 employees.setId(rs.getInt("EMPNO"));
							 employees.setName(rs.getString("ENAME"));
							 employees.setDesignation(rs.getString("JOB"));
							 employees.setJoiningDate(rs.getString("HIREDATE"));
							 employees.setSalary(rs.getDouble("SAL"));
				          
							  listEmployee.add(employees);
						 }
			    	
						 cMessageType.printEmployees(listEmployee, "small");
					 } else {
						  cMessageType.show("No records found for the employee id : " + empId);
					 }
			    } catch(SQLException sqle) {
			    	cMessageType.showError(sqle.getMessage());
			    	connection.close();
				} finally {
					connection.close();  
				}
		  } catch (SQLException connE) {
		    	cMessageType.showError(connE.getMessage());
		  }
	  }

	  /**
	   * Selects all the rows from the employee table.
	   * @param connection
	   */
	  @Override
	  public void selectAll() {
		  
		  OracleConnection connection;
		  try {
				connection = oraDbConfiguration.getConnection();

				ArrayList<Employee> listEmployee = new ArrayList<Employee>();
		  
				try(Statement stmt = connection.createStatement()) {
					ResultSet rs = stmt.executeQuery(SQL_SELECT_ALL);

					if (rs.next()) {
		    	  
						while (rs.next()) {
							Employee employees = new Employee();
							employees.setId(rs.getInt("EMPNO"));
							employees.setName(rs.getString("ENAME"));
							employees.setDesignation(rs.getString("JOB"));
							employees.setJoiningDate(rs.getString("HIREDATE"));
							employees.setSalary(rs.getDouble("SAL"));
							listEmployee.add(employees);
						}
		    	
						cMessageType.printEmployees(listEmployee, "small");
					} else {
						cMessageType.show("No records found");
					}
		    
				} catch(SQLException sqle) {
					cMessageType.showError(sqle.getMessage());
					connection.close();
				} finally {
					connection.close();  
				}
		  	} catch (SQLException connE) {
		    	cMessageType.showError(connE.getMessage());
		  	}
	  }
}
