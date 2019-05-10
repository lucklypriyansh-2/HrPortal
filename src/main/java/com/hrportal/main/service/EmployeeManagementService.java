package com.hrportal.main.service;

import java.util.LinkedHashMap;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.hrportal.main.exception.PortalException;
import com.hrportal.main.model.EmployeeNode;
import com.hrportal.main.repository.RelationShipRepository;

@Service
public class EmployeeManagementService {

	@Autowired
	RelationShipRepository edgeRepository;

	@Autowired
	TreeService treeservice;

	/**
	 * save Hierarchy of data and detect invalid data if any 1. Init tree data 2. generate tree
	 * 
	 * @param jsonNode
	 * @return LinkedHashMap<String, Object>
	 */
	@Transactional
	public LinkedHashMap<String, Object> saveHeriarchy(JsonNode jsonNode) {
		treeservice.initTree(jsonNode);
		return treeservice.generateTree();
	}

	/**
	 * api to find supervisor using recursive approach
	 * 
	 * @param employee
	 * @return
	 */
	public EmployeeNode findSuperVisor(String employee) {

		EmployeeNode employeeNode = new EmployeeNode();
		employeeNode.setEmployeeName(employee);
		if (edgeRepository.findByEmployee(employee).size() > 0) {
			employeeNode.setSupervisor(findSuperVisor(employee, 2));
			return employeeNode;
		}
		throw new PortalException("Not a valid employee");
	}

	/**
	 * 
	 * @param employee
	 * @param depth
	 * @return List<EmployeeNode>
	 */
	private EmployeeNode findSuperVisor(String employee, int depth) {
		if (depth <= 0) {
			return null;
		}
		EmployeeNode employees = new EmployeeNode();

		edgeRepository.findByEmployee(employee).forEach(e -> {

			int depthinside = depth - 1;

			employees.setEmployeeName(e.getSupervisor());
			employees.setSupervisor(findSuperVisor(e.getSupervisor(), depthinside));

		});
		return employees;
	}

}
