package com.mas.oracledb.controller;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import com.mas.oracledb.configuration.OracleConfiguration;
import com.mas.oracledb.util.CustomMessageType;

public class OraDbPlSqlController {

	private static CustomMessageType cMessageType = new CustomMessageType("OracleDbXmlController");
	  private OracleConfiguration oraDbConfiguration = new OracleConfiguration();
	
	private void PlsqlProcedureNoParams(Connection conn, String TABLE_NAME) throws SQLException {
	    // Create a PLSQL stored procedure that takes no arguments.
	    final String PROC_NAME = "ProcNoParams";
	    String sql = "CREATE OR REPLACE PROCEDURE "+PROC_NAME+" IS "
	                  + "BEGIN "
	                  + "INSERT INTO "+TABLE_NAME+" VALUES (5, 'FIVE', '"+PROC_NAME+"'); "
	                  + "INSERT INTO "+TABLE_NAME+" VALUES (6, 'SIX', '"+PROC_NAME+"'); "
	                  + "INSERT INTO "+TABLE_NAME+" VALUES (7, 'SEVEN', '"+PROC_NAME+"'); "
	                  + "INSERT INTO "+TABLE_NAME+" VALUES (8, 'EIGHT', '"+PROC_NAME+"'); "
	                + "END; ";
	    cMessageType.show(sql);
	    oraDbConfiguration.doSql(conn, sql);
	    
	    // Invoke the stored procedure.
	    sql = "CALL "+PROC_NAME+"()";
	    try (CallableStatement callStmt = conn.prepareCall(sql)) {
	      callStmt.execute();
	      
	      // Display rows inserted by the above stored procedure call.
	      cMessageType.show("Rows inserted by the stored procedure '"+PROC_NAME+"' are: ");
	      displayRows(conn, PROC_NAME, TABLE_NAME);
	    } catch (SQLException sqlEx) {
	    	cMessageType.showError("PlsqlProcedureNoArgs", sqlEx);
	    } finally {
	      // Drop the procedure when done with it.
	    	oraDbConfiguration.doSql(conn, "DROP PROCEDURE "+PROC_NAME);
	    }
	  }
	  
	  private void PlsqlProcedureINParams(Connection conn, String TABLE_NAME) throws SQLException {
	    // Create a PLSQL stored procedure with IN parameters.
	    final String PROC_NAME = "ProcINParams";
	    String sql = "CREATE OR REPLACE PROCEDURE "+PROC_NAME+"(num IN NUMBER, name IN VARCHAR2, insertedBy IN VARCHAR2) IS "
	        + "BEGIN "
	        + "INSERT INTO "+TABLE_NAME+" VALUES (num, name, insertedBy); "
	      + "END; ";
	    cMessageType.show(sql);
	    oraDbConfiguration.doSql(conn, sql);
	    
	    // Invoke the stored procedure.
	    sql = "CALL "+PROC_NAME+"(?,?,?)";
	    try (CallableStatement callStmt = conn.prepareCall(sql)) {
	      callStmt.setInt(1, 9);
	      callStmt.setString(2, "NINE");
	      callStmt.setString(3, PROC_NAME);
	      callStmt.addBatch();
	      
	      callStmt.setInt(1, 10);
	      callStmt.setString(2, "TEN");
	      callStmt.setString(3, PROC_NAME);
	      callStmt.addBatch();
	      
	      callStmt.setInt(1, 11);
	      callStmt.setString(2, "ELEVEN");
	      callStmt.setString(3, PROC_NAME);
	      callStmt.addBatch();
	      
	      callStmt.setInt(1, 12);
	      callStmt.setString(2, "TWELVE");
	      callStmt.setString(3, PROC_NAME);
	      callStmt.addBatch();
	      
	      callStmt.executeBatch();
	      
	      // Display rows inserted by the above stored procedure call.
	      cMessageType.show("Rows inserted by the stored procedure '"+PROC_NAME+"' are: ");
	      displayRows(conn, PROC_NAME, TABLE_NAME);
	    } catch (SQLException sqlEx) {
	    	cMessageType.showError("PlsqlProcedureINParams", sqlEx);
	    } finally {
	      // Drop the procedure when done with it.
	    	oraDbConfiguration.doSql(conn, "DROP PROCEDURE "+PROC_NAME);
	    }
	  }
	  
	  private void PlsqlProcedureOUTParams(Connection conn, String TABLE_NAME) throws SQLException {
	    // Create a PLSQL stored procedure with OUT parameters.
	    final String PROC_NAME = "ProcOUTParams";
	    String sql = "CREATE OR REPLACE PROCEDURE "+PROC_NAME+"(num IN NUMBER, name IN VARCHAR2, insertedBy IN VARCHAR2, numInserted OUT NUMBER) IS "
	        + "BEGIN "
	        + "INSERT INTO "+TABLE_NAME+" VALUES (num, name, insertedBy); "
	        + "numInserted := num; "
	      + "END; ";
	    cMessageType.show(sql);
	    oraDbConfiguration.doSql(conn, sql);
	    
	    // Invoke the stored procedure.
	    sql = "CALL "+PROC_NAME+"(?,?,?,?)";
	    try (CallableStatement callStmt = conn.prepareCall(sql)) {
	      callStmt.setInt(1, 13);
	      callStmt.setString(2, "THIRTEEN");
	      callStmt.setString(3, PROC_NAME);
	      callStmt.registerOutParameter(4, Types.INTEGER);
	      callStmt.execute();
	      
	      // Display rows inserted by the above stored procedure call.
	      cMessageType.show("Rows inserted by the stored procedure '"+PROC_NAME+"' are: ");
	      displayRows(conn, PROC_NAME, TABLE_NAME);
	      
	      // Show the value of OUT parameter after the stored procedure call.
	      cMessageType.show("The out parameter value of stored procedure '"+PROC_NAME+"' returned "+callStmt.getInt(4)+".");
	      
	    } catch (SQLException sqlEx) {
	    	cMessageType.showError("PlsqlProcedureOUTParams", sqlEx);
	    } finally {
	      // Drop the procedure when done with it.
	    	oraDbConfiguration.doSql(conn, "DROP PROCEDURE "+PROC_NAME);
	    }
	  }
	  
	  private void PlsqlProcedureINOUTParams(Connection conn, String TABLE_NAME) throws SQLException {
	    // Create a PLSQL stored procedure with IN OUT parameters.
	    final String PROC_NAME = "ProcINOUTParams";
	    String sql = "CREATE OR REPLACE PROCEDURE "+PROC_NAME+"(num IN OUT NUMBER, name IN OUT VARCHAR2, insertedBy IN VARCHAR2) IS "
	        + "BEGIN "
	        + "INSERT INTO "+TABLE_NAME+" VALUES (num, name, insertedBy); "
	        + "num := 0; "
	        + "name := 'ZERO'; "
	      + "END; ";
	    cMessageType.show(sql);
	    oraDbConfiguration.doSql(conn, sql);
	    
	    // Invoke the stored procedure.
	    sql = "CALL "+PROC_NAME+"(?,?,?)";
	    try (CallableStatement callStmt = conn.prepareCall(sql)) {
	      callStmt.setInt(1, 14);
	      callStmt.registerOutParameter(1, Types.INTEGER);
	      
	      callStmt.setString(2, "FOURTEEN");
	      callStmt.registerOutParameter(2, Types.VARCHAR);
	      
	      callStmt.setString(3, PROC_NAME);
	      callStmt.execute();
	      
	      // Display rows inserted by the above stored procedure call.
	      cMessageType.show("Rows inserted by the stored procedure '"+PROC_NAME+"' are: ");
	      displayRows(conn, PROC_NAME, TABLE_NAME);
	      
	      // Show the values of OUT parameters after the stored procedure call.
	      cMessageType.show("Out parameter values of stored procedure '" + PROC_NAME + "': num = " + callStmt.getInt(1)
	          + ", name = " + callStmt.getString(2) + ".");
	    } catch (SQLException sqlEx) {
	      cMessageType.showError("PlsqlProcedureINOUTParams", sqlEx);
	    } finally {
	      // Drop the procedure when done with it.
	      oraDbConfiguration.doSql(conn, "DROP PROCEDURE "+PROC_NAME);
	    }
	  }
	  
	  private void PlsqlFunctionNoParams(Connection conn, String TABLE_NAME) throws SQLException {
	    // Create a PLSQL function that takes no arguments.
	    final String FUNC_NAME = "FuncNoParams";
	    String sql = "CREATE OR REPLACE FUNCTION "+FUNC_NAME+" RETURN NUMBER IS "
	        + "BEGIN "
	        + "INSERT INTO "+TABLE_NAME+" VALUES (15, 'FIFTEEN', '"+FUNC_NAME+"'); "
	        + "INSERT INTO "+TABLE_NAME+" VALUES (16, 'SIXTEEN', '"+FUNC_NAME+"'); "
	        + "INSERT INTO "+TABLE_NAME+" VALUES (17, 'SEVENTEEN', '"+FUNC_NAME+"'); "
	        + "INSERT INTO "+TABLE_NAME+" VALUES (18, 'EIGHTEEN', '"+FUNC_NAME+"'); "
	        + "RETURN 4;"   // Return number of row inserted into the table.
	      + "END; ";
	    cMessageType.show(sql);
	    oraDbConfiguration.doSql(conn, sql);
	    
	    // Invoke the PLSQL function.
	    sql = "BEGIN ? := "+FUNC_NAME+"; end;";
	    try (CallableStatement callStmt = conn.prepareCall(sql)) {
	      callStmt.registerOutParameter (1, Types.INTEGER);
	      callStmt.execute();
	      
	      // Display rows inserted by the above PLSQL function call.
	      cMessageType.show("Rows inserted by the PLSQL function '"+FUNC_NAME+"' are: ");
	      displayRows(conn, FUNC_NAME, TABLE_NAME);
	      
	      // Show the value returned by the PLSQL function.
	      cMessageType.show("The value returned by the PLSQL function '"+FUNC_NAME+"' is "+callStmt.getInt(1)+".");
	    } catch (SQLException sqlEx) {
	      cMessageType.showError("PlsqlFunctionNoParams", sqlEx);
	    } finally {
	      // Drop the function when done with it.
	      oraDbConfiguration.doSql(conn, "DROP FUNCTION "+FUNC_NAME);
	    }
	  }
	  
	  private void PlsqlFunctionINParams(Connection conn, String TABLE_NAME) throws SQLException {
	    // Create a PLSQL function with IN parameters.
	    final String FUNC_NAME = "FuncINParams";
	    String sql = "CREATE OR REPLACE FUNCTION "+FUNC_NAME+"(num IN NUMBER, name IN VARCHAR2, insertedBy IN VARCHAR2) RETURN NUMBER IS "
	        + "BEGIN "
	        + "INSERT INTO "+TABLE_NAME+" VALUES (num, name, insertedBy); "
	        + "RETURN 1;"   // Return number of row inserted into the table.
	      + "END; ";
	    cMessageType.show(sql);
	    oraDbConfiguration.doSql(conn, sql);
	    
	    // Invoke the PLSQL function.
	    sql = "BEGIN ? := "+FUNC_NAME+"(?,?,?); end;";
	    try (CallableStatement callStmt = conn.prepareCall(sql)) {
	      callStmt.registerOutParameter (1, Types.INTEGER);
	      callStmt.setInt(2, 19);
	      callStmt.setString(3, "NINETEEN");
	      callStmt.setString(4, FUNC_NAME);
	      callStmt.execute();
	      
	      // Display rows inserted by the above PLSQL function call.
	      cMessageType.show("Rows inserted by the PLSQL function '"+FUNC_NAME+"' are: ");
	      displayRows(conn, FUNC_NAME, TABLE_NAME);
	      
	      // Show the value returned by the PLSQL function.
	      cMessageType.show("The value returned by the PLSQL function '"+FUNC_NAME+"' is "+callStmt.getInt(1)+".");
	    } catch (SQLException sqlEx) {
	      cMessageType.showError("PlsqlFunctionINParams", sqlEx);
	    } finally {
	      // Drop the function when done with it.
	      oraDbConfiguration.doSql(conn, "DROP FUNCTION "+FUNC_NAME);
	    }
	  }
	  
	  private void PlsqlFunctionOUTParams(Connection conn, String TABLE_NAME) throws SQLException {
	    // Create a PLSQL function with IN parameters.
	    final String FUNC_NAME = "FuncOUTParams";
	    String sql = "CREATE OR REPLACE FUNCTION "+FUNC_NAME+"(num IN NUMBER, name IN VARCHAR2, insertedBy IN VARCHAR2, numInserted OUT NUMBER) RETURN NUMBER IS "
	        + "BEGIN "
	        + "INSERT INTO "+TABLE_NAME+" VALUES (num, name, insertedBy); "
	        + "numInserted := num; "
	        + "RETURN 1;"   // Return number of row inserted into the table.
	      + "END; ";
	    cMessageType.show(sql);
	    oraDbConfiguration.doSql(conn, sql);
	    
	    // Invoke the PLSQL function.
	    sql = "BEGIN ? := "+FUNC_NAME+"(?,?,?,?); end;";
	    try (CallableStatement callStmt = conn.prepareCall(sql)) {
	      callStmt.registerOutParameter (1, Types.INTEGER);
	      callStmt.setInt(2, 20);
	      callStmt.setString(3, "TWENTY");
	      callStmt.setString(4, FUNC_NAME);
	      callStmt.registerOutParameter(5, Types.INTEGER);
	      callStmt.execute();
	      
	      // Display rows inserted by the above PLSQL function call.
	      cMessageType.show("Rows inserted by the PLSQL function '"+FUNC_NAME+"' are: ");
	      displayRows(conn, FUNC_NAME, TABLE_NAME);
	      
	      // Show the value returned by the PLSQL function.
	      cMessageType.show("The value returned by the PLSQL function '"+FUNC_NAME+"' is "+callStmt.getInt(1)+".");
	      
	      // Show the values of OUT parameters after the PLSQL function call.
	      cMessageType.show("Out parameter value of PLSQL function '" + FUNC_NAME + "': num = " + callStmt.getInt(5) + ".");
	    } catch (SQLException sqlEx) {
	      cMessageType.showError("PlsqlFunctionOUTParams", sqlEx);
	    } finally {
	      // Drop the function when done with it.
	      oraDbConfiguration.doSql(conn, "DROP FUNCTION "+FUNC_NAME);
	    }
	  }
	  
	  private void PlsqlFunctionINOUTParams(Connection conn, String TABLE_NAME) throws SQLException {
	    // Create a PLSQL function with IN OUT parameters.
	    final String FUNC_NAME = "FuncINOUTParams";
	    String sql = "CREATE OR REPLACE FUNCTION "+FUNC_NAME+"(num IN OUT NUMBER, name IN OUT VARCHAR2, insertedBy IN VARCHAR2) RETURN NUMBER IS "
	        + "BEGIN "
	        + "INSERT INTO "+TABLE_NAME+" VALUES (num, name, insertedBy); "
	        + "num := 0; "
	        + "name := 'ZERO'; "
	        + "RETURN 1;"   // Return number of row inserted into the table.
	      + "END; ";
	    cMessageType.show(sql);
	    oraDbConfiguration.doSql(conn, sql);
	    
	    // Invoke the PLSQL function.
	    sql = "BEGIN ? := "+FUNC_NAME+"(?,?,?); end;";
	    try (CallableStatement callStmt = conn.prepareCall(sql)) {
	      callStmt.registerOutParameter (1, Types.INTEGER);
	      
	      callStmt.registerOutParameter (2, Types.INTEGER);
	      callStmt.setInt(2, 20);
	      
	      callStmt.registerOutParameter (3, Types.VARCHAR);
	      callStmt.setString(3, "TWENTY");
	      
	      callStmt.setString(4, FUNC_NAME);
	      callStmt.execute();
	      
	      // Display rows inserted by the above PLSQL function call.
	      cMessageType.show("Rows inserted by the PLSQL function '"+FUNC_NAME+"' are: ");
	      displayRows(conn, FUNC_NAME, TABLE_NAME);
	      
	      // Show the value returned by the PLSQL function.
	      cMessageType.show("The value returned by the PLSQL function '"+FUNC_NAME+"' is "+callStmt.getInt(1)+".");
	      
	      // Show the values of OUT parameters after the PLSQL function call.
	      cMessageType.show("Out parameter values of PLSQL function '" + FUNC_NAME + "': num = " + callStmt.getInt(2)
	          + ", name = " + callStmt.getString(3) + ".");
	    } catch (SQLException sqlEx) {
	      cMessageType.showError("PlsqlFunctionINOUTParams", sqlEx);
	    } finally {
	      // Drop the function when done with it.
	      oraDbConfiguration.doSql(conn, "DROP FUNCTION "+FUNC_NAME);
	    }
	  }
	  
	  public void displayRows(Connection conn, String insertedByBind, String TABLE_NAME) throws SQLException {
	    
	    String sql = "SELECT * FROM "+TABLE_NAME+" WHERE insertedBy = ?";
	    try (PreparedStatement prepStmt = conn.prepareStatement(sql)) {
	      prepStmt.setString(1, insertedByBind);
	      
	      ResultSet rs = prepStmt.executeQuery();
	      while (rs.next()) {
	        cMessageType.show(rs.getInt(1)+"\t"+rs.getString(2)+"\t"+rs.getString(3));
	      }
	    }
	  }

	  public void Execute() {
		    try (Connection conn = oraDbConfiguration.getConnection()) {

		      // Initialize the table
		      init(conn);

		      // nstrate how a no arg PLSQL procedure can be invoked.
		      PlsqlProcedureNoParams(conn, "PLSQL_JDBC_SAMPLE");

		      // nstrate how a PLSQL procedure with IN parameters can be invoked.
		      PlsqlProcedureINParams(conn, "PLSQL_JDBC_SAMPLE");

		      // nstrate how a PLSQL procedure with OUT parameters can be invoked.
		      PlsqlProcedureOUTParams(conn, "PLSQL_JDBC_SAMPLE");

		      // nstrate how a PLSQL procedure with IN OUT parameters can be invoked.
		      PlsqlProcedureINOUTParams(conn, "PLSQL_JDBC_SAMPLE");

		      // nstrate how a no arg PLSQL function can be invoked.
		      PlsqlFunctionNoParams(conn, "PLSQL_JDBC_SAMPLE");

		      // nstrate how a PLSQL function with IN parameters can be invoked.
		      PlsqlFunctionINParams(conn, "PLSQL_JDBC_SAMPLE");

		      // nstrate how a PLSQL function with OUT parameters can be invoked.
		      PlsqlFunctionOUTParams(conn, "PLSQL_JDBC_SAMPLE");

		      // nstrate how a PLSQL function with IN OUT parameters can be invoked.
		      PlsqlFunctionINOUTParams(conn, "PLSQL_JDBC_SAMPLE");

		      // Cleanup the database after the .
		      oraDbConfiguration.truncateTable(conn, "PLSQL_JDBC_SAMPLE");
		    } catch (SQLException sqlEx) {
		      cMessageType.showError("run", sqlEx);
		    }
		  }

		  private void init(Connection conn) throws SQLException {

		    // Truncate the table.
			oraDbConfiguration.truncateTable(conn, "PLSQL_JDBC_SAMPLE");

		    // Load the table with few rows.
			oraDbConfiguration.loadTable(conn, "PLSQL_JDBC_SAMPLE");
		  }

}
