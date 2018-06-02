package com.mas.oracledb.model;


/**
 * Author: MAS - Marcos Almeida Santos
 * 
 * Description: Simple sample of CRUD operation using the Oracle Statement object.
 */

public class Employee {

  private int id;
  
  private String name;
  
  private String designation;
  
  private String joiningDate;
  
  private double salary;

  
 public Employee() {
    super();
    this.id = getId();
    this.name = getName();
    this.designation = getDesignation();
    this.joiningDate = getJoiningDate();
    this.salary = getSalary();
  }

 

public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDesignation() {
    return designation;
  }

  public String getJoiningDate() {
    return joiningDate;
  }

  public double getSalary() {
    return salary;
  }


public void setId(int id) {
	this.id = id;
}


public void setName(String name) {
	this.name = name;
}


public void setDesignation(String designation) {
	this.designation = designation;
}


public void setJoiningDate(String joiningDate) {
	this.joiningDate = joiningDate;
}


public void setSalary(double salary) {
	this.salary = salary;
}
 

}