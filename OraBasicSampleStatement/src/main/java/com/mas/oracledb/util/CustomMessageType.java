package com.mas.oracledb.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mas.oracledb.model.Employee;
import com.mas.oracledb.model.EmployeeDate;

/**
 * Author: MAS - Marcos Almeida Santos
 * 
 * Description: Simple sample of CRUD operation using the Oracle Statement object.
 */

public class CustomMessageType {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(CustomMessageType.class);
	
	private String errorMessage;

    public CustomMessageType(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
    
    public void show(String msg) {
        LOGGER.info(errorMessage + " - " + msg);
      }

    public void showError(String msg) {
		LOGGER.error("Error : " + errorMessage + " - " + msg);
      }
    
    public void showError(String msg, SQLException e) {
		LOGGER.error("Error : " + errorMessage + " - " + e.getMessage());
      }
    
    
    public void printEmployees(ArrayList<Employee> listEmployees, String type) {
    	for(Employee employee : listEmployees)
    	{
    		show("/----------------------------------------------------------------/");
            show("ID          : " + employee.getId());
            show("NAME        : " + employee.getName());
            show("Designation : " + employee.getDesignation());
            show("Joining Date: " + employee.getJoiningDate());
            show("Salary      : " + employee.getSalary());
            show(
                "/----------------------------------------------------------------/");
    	}
    }
    
    public void printEmployeesDate(ArrayList<EmployeeDate> listEmployeesDate, String type) {
    	for(EmployeeDate employee : listEmployeesDate)
    	{

	      show("/----------------------------------------------------------------/");
	      show("ID                   : " + employee.getId());
	      show("Date Of Birth        : " + employee.getDateOfBirth());
	      show("Joining Date         : " + employee.getJoiningDate());
	      show("Resignation Date     : " + employee.getResignationDate());
	      show("Date of Leaving      : " + employee.getDateOfLeaving());
	      show("/----------------------------------------------------------------/\n");
	    
    	}
    }
    
    	/*
    	for(int i = 0; i < listaPessoas.size(); i++)
    	{
    	    listaPessoas.get(i).fazerAlgumaCoisa();
    	}
    	*/
    	
    	
    	/**
  	   * Reads the password from console.
  	   *
  	   * @param prompt
  	   * @throws Exception
  	   */
  	public String readPassword(String prompt) throws Exception {
  		  
  		String password = null;
  	    if (System.console() != null) {
  	    	char[] pchars = System.console().readPassword("\n[%s]", prompt);
  	    	if (pchars != null) {
  	    		password = new String(pchars);
  	    		java.util.Arrays.fill(pchars, ' ');
  	    	}
  	    } else {
  	    	BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
  	    	show(prompt);
  	    	password = r.readLine();
  	    }
  	    return password;
  	}
    
}
