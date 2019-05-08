package com.hrportal.main.model;

import java.util.ArrayList;
import java.util.List;

public class EmployeeNode {

	
	String employeeName;

	List<EmployeeNode> superVisors = new ArrayList<EmployeeNode>();

	public List<EmployeeNode> getSuperVisors() {
		return superVisors;
	}

	public void setSuperVisors(List<EmployeeNode> superVisors) {
		this.superVisors = superVisors;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

}
