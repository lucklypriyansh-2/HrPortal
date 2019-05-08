package com.hrportal.main.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.hrportal.main.model.EmployeeNode;
import com.hrportal.main.repository.RelationShipRepository;

@Service
public class EmployeeManagementService {

	@Autowired
	RelationShipRepository edgeRepository;

	@Autowired
	TreeService treeservice;

	/**
	 * save Heriachy of data and detect invalid data if any 1. Init tree data 2. generate tree
	 * 
	 * @param jsonNode
	 * @return LinkedHashMap<String, Object>
	 */
	@Transactional
	public LinkedHashMap<String, Object> saveHeriarchy(JsonNode jsonNode) {
		treeservice.initTree(jsonNode);
		return treeservice.generateTree();
	}

	@Transactional
	public EmployeeNode findSuperVisor(String employee) {

		EmployeeNode employeeNode = new EmployeeNode();
		employeeNode.setEmployeeName(employee);

		employeeNode.setSuperVisors(findSuperVisor(employee, 2));
		return employeeNode;
	}
	
	
	/**
	 * 
	 * @param employee
	 * @param depth
	 * @return List<EmployeeNode>
	 */
	private List<EmployeeNode> findSuperVisor(String employee, int depth) {
		if (depth <= 0) {
			return null;
		}
		List<EmployeeNode> employees = new ArrayList<EmployeeNode>();
		
		edgeRepository.findByEmployee(employee).forEach(e -> {
			
			int depthinside=depth-1;
			EmployeeNode employeeNode = new EmployeeNode();
			employeeNode.setEmployeeName(e.getSupervisor());
			employeeNode.setSuperVisors(findSuperVisor(e.getSupervisor(),depthinside));
			employees.add(employeeNode);

		});
		return employees;
	}

}
