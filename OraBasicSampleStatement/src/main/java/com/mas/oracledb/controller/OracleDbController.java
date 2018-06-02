package com.mas.oracledb.controller;

import java.sql.SQLException;
import java.util.Scanner;

import com.mas.oracledb.model.Employee;
import com.mas.oracledb.service.EmployeeService;
import com.mas.oracledb.service.EmployeeServiceImpl;
import com.mas.oracledb.util.CustomMessageType;

import oracle.jdbc.OracleConnection;

/**
 * Author: MAS - Marcos Almeida Santos
 * 
 * Description: Simple sample of CRUD operation using the Oracle Statement object.
 */

public class OracleDbController {
	
	private static final int USER_OPTION_SELECTALL = 1;
	private static final int USER_OPTION_SELECTONE = 2;
	private static final int USER_OPTION_INSERT = 3;
	private static final int USER_OPTION_UPDATE = 4;
	private static final int USER_OPTION_DELETE = 5;
	private static final int USER_OPTION_EXIT = 0;
	
	static CustomMessageType cMessageType = new CustomMessageType("OracleDbController");

	/**
	   * Get a connection from the Oracle Database.
	   * and performs CRUD operation based on the user input.
	   * @throws SQLException
	   */
	  public void startDemo() {

		  EmployeeServiceImpl employeeService = new EmployeeServiceImpl();

	    try {
	      // loops forever until the user choose to exit.
	      while (true) {
	        int userOption = getUserOption();
	        
	        switch(userOption) {
	        case USER_OPTION_SELECTONE :
	        	employeeService.selectOne();
	          break;
	        case USER_OPTION_SELECTALL :
	        	employeeService.selectAll();
	          break;
	        case USER_OPTION_INSERT :
	        	employeeService.insert();
	          break;
	        case USER_OPTION_UPDATE :
	        	employeeService.update();
	          break;
	        case USER_OPTION_DELETE :
	        	employeeService.delete();
	          break;
	        case USER_OPTION_EXIT :
	        	cMessageType.show("See ya !!");
	          return;
	         default :
	        	 cMessageType.show("Invalid option : " + userOption);
	        }
	      }
	    } catch (Exception e) {
	    	cMessageType.showError("getOptionValue: " + e.getMessage());
	    }
	  }
	
	// Get specified option value from command-line, or use default value
	  public static String getCmdOptionValue(String args[], String optionName,
	      String defaultVal) {
	    String argValue = "";
	    try {
	      int i = 0;
	      String arg = "";
	      boolean found = false;
	      while (i < args.length) {
	        arg = args[i++];
	        if (arg.equals(optionName)) {
	          if (i < args.length)
	            argValue = args[i++];
	          
	          	System.out.println(argValue); 
	          
	          if (argValue.startsWith("-") || argValue.equals("")) {
	        	  cMessageType.show("No value for Option " + optionName + ", use default.");
	            argValue = defaultVal;
	          }
	          found = true;
	        }
	      }

	      if (!found) {
	    	  cMessageType.show("No Option " + optionName + " specified, use default.");
	        argValue = defaultVal;
	      }
	    }
	    catch (Exception e) {
	    	cMessageType.showError("getOptionValue" + e.getMessage());
	    }
	    return argValue;
	  }

	  /**
	   * Get the user option to perform the table operation.
	   *
	   * @return
	   */
	  public static int getUserOption() {
	    int userOption = -1;
	    try {
	      Scanner scanner = new Scanner(System.in);
	      System.out.println(
	          "1 - Select All, 2 - Select One, 3 - Insert, 4 - Update, 5 - Delete, 0 - Exit");
	      System.out.println("Enter Option :");
	      userOption = Integer.parseInt(scanner.nextLine());
	      System.out.println("Option choosed: " + userOption);
	    }
	    catch (Exception e) {
	    	cMessageType.showError("getUserOption" + e.getMessage());
	    }
	    return userOption;
	  }

	  /**
	   * An utility method to get the employee details from the user.
	   *
	   * @return employeeObj
	   */
	  public static Employee getEmployeeFromConsole() {
	    Employee empObj = null;
	    ;
	    try {
	      Scanner scanner = new Scanner(System.in);
	      System.out.println("Enter Employee Details");
	      System.out.println("ID : ");
	      int id = Integer.parseInt(scanner.nextLine());
	      System.out.println("Name : ");
	      String name = scanner.nextLine();
	      System.out.println("Designation : ");
	      String designation = scanner.nextLine();
	      System.out.println("Joining Date(yyyy/mm/dd) : ");
	      String joiningDate = scanner.nextLine();
	      System.out.println("Salary : ");
	      double salary = Double.parseDouble(scanner.nextLine());
	      
	      empObj.setId(id);
	      empObj.setName(name);
	      empObj.setDesignation(designation);
	      empObj.setJoiningDate(joiningDate);
	      empObj.setSalary(salary);

	    }
	    catch (Exception e) {
	    	cMessageType.showError("getEmployeeFromConsole" + e.getMessage());
	    }
	    return empObj;
	  }

	  /**
	   * An utility method to get the employee id from the user.
	   *
	   * @return employeeID
	   */
	  public static int getEmployeeIDFromConsole() {
	    int empId = -1;
	    try {
	      Scanner scanner = new Scanner(System.in);
	      System.out.println("Enter Employee ID :");
	      empId = Integer.parseInt(scanner.nextLine());
	      System.out.println("Employee ID choosed:" + empId);
	    }
	    catch (Exception e) {
	    	cMessageType.showError("getEmployeeIDFromConsole" + e.getMessage());
	    }
	    return empId;
	  }
}
