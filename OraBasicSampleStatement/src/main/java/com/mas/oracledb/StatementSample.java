package com.mas.oracledb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import com.mas.oracledb.controller.OracleDbController;
import com.mas.oracledb.model.Employee;
import com.mas.oracledb.util.CustomMessageType;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.pool.OracleDataSource;

/**
 * Author: MAS - Marcos Almeida Santos
 * 
 * Description: Simple sample of access of Data Base Oracle 12C
 */

public class StatementSample {

	// Start the main with the command "java StatementDemo -u "<user>" -l "<URL>"
	public static void main(String args[]) throws IOException {

		OracleDbController oraControl = new OracleDbController();
		oraControl.startDemo();
		
		
	}
  
}
