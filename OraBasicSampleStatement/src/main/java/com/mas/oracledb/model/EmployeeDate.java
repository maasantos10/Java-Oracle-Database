package com.mas.oracledb.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * Author: MAS - Marcos Almeida Santos
 * 
 * Description: Class of employee Model
 */


public class EmployeeDate {

	private int id;
    private Date dateOfBirth;
    private LocalDateTime joiningDate;
    private ZonedDateTime dateOfResignation;
    private Timestamp dateOfLeaving;

    public EmployeeDate() {
    	super();
    }

    public int getId() {
      return id;
    }

    public Date getDateOfBirth() {
      return this.dateOfBirth;
    }

    public LocalDateTime getJoiningDate() {
      return this.joiningDate;
    }

    public ZonedDateTime getResignationDate() {
      return this.dateOfResignation;
    }

    public Timestamp getDateOfLeaving() {
      return this.dateOfLeaving;
    }

	public void setId(int id) {
		this.id = id;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setJoiningDate(LocalDateTime joiningDate) {
		this.joiningDate = joiningDate;
	}

	public void setDateOfResignation(ZonedDateTime dateOfResignation) {
		this.dateOfResignation = dateOfResignation;
	}

	public void setDateOfLeaving(Timestamp dateOfLeaving) {
		this.dateOfLeaving = dateOfLeaving;
	}

    
}
