package com.mas.oracledb;

import com.mas.oracledb.controller.OracleDbJsonController;

/**
 * Author: MAS - Marcos Almeida Santos
 * 
 * Purpose: Put forward how we can work with json and DB Oracle  
 */

public class JsonOraDb {

	public static void main(String args[]) throws Exception {

	    //String[] arqsUPU = new String[] {"-u", "-l"};
		//args = arqsUPU;

		OracleDbJsonController jsonOraController = new OracleDbJsonController();
		jsonOraController.ExecuteJsonRoutines();

	}

}
