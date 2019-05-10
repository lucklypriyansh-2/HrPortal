package com.hrportal.main.model;

public class EmployeeNode {

	
	String employeeName;

	EmployeeNode supervisor;

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public EmployeeNode getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(EmployeeNode supervisor) {
		this.supervisor = supervisor;
	}



}
