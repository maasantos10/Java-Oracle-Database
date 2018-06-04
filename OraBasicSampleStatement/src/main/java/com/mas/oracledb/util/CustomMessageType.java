package com.mas.oracledb.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

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
    
    public void showError(String msg, Throwable exc) {
    	LOGGER.error(msg + " hit error: " + exc.getMessage());
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
    
    
    /***
     * Obs.: In this case it is so much better include all atributes of Class EmployeeDate 
     * in only one Class such a Employee Class, however, I want to leave in two separate classes
     * for study purpose.
     * 
     * @param listEmployeesDate
     * @param type
     */
    
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
	   * Simple code to print an XML Documint to an OutputStream.
	   *
	   * @param doc an XML document to print
	   * @param the stream to print to
	   * @throws IOException if an error occurs is writing the output
	   * @throws TransformerException if an error occurs in generating the output
	   */
	  public static void printDocument(Document doc, OutputStream out)
	    throws IOException, TransformerException {
	    TransformerFactory factory = TransformerFactory.newInstance();
	    Transformer transformer = factory.newTransformer();
	    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
	    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

	    transformer.transform(new DOMSource(doc),
	         new StreamResult(new OutputStreamWriter(out, "UTF-8")));
	  }
    	
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
