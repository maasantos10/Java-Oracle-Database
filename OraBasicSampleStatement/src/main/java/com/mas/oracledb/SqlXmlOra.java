package com.mas.oracledb;

import com.mas.oracledb.controller.OracleDbXmlController;

/**
 * Author: MAS - Marcos Almeida Santos
 * 
 * Purpose: Sample with xml within oracle database.
 */

public class SqlXmlOra {
	  
	  /**
	   * Create an instance, get the database connection and run sample.
	   *
	   * @param args command line args
	   * @throws Exception if an error occurs
	   */
	  public static void main(String args[]) throws Exception {
		  OracleDbXmlController OraXml = new OracleDbXmlController();
		  OraXml.ExecuteAllXmlSamples();
	  }

}
