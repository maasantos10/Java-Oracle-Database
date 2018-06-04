package com.mas.oracledb;

import com.mas.oracledb.controller.OraDbPlSqlController;

public class PLSqlOra {

	public static void main(String args[]) throws Exception {
		
		OraDbPlSqlController oraPlSqlCtrl = new OraDbPlSqlController();
		oraPlSqlCtrl.Execute();
	}
}
