package com.hrportal.main.controller;

import java.io.IOException;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrportal.main.aspect.Authenticate;
import com.hrportal.main.exception.PortalBadRequestException;
import com.hrportal.main.model.EmployeeNode;
import com.hrportal.main.service.EmployeeManagementService;

import net.sf.json.JSONObject;

@RestController
public class EmployeeManagementController {

	@Autowired
	EmployeeManagementService employeeManagementService;

	@PostMapping("/Hierarchy")
	@Authenticate
	public LinkedHashMap<String, Object> saveHeriarchy(HttpServletRequest request,@RequestBody String requestBody) {
		try {
			JSONObject jsonObject = JSONObject.fromObject(requestBody);
			JsonNode   jsonNode   = new ObjectMapper().readTree(jsonObject.toString());

			return employeeManagementService.saveHeriarchy(jsonNode);

		} catch (IOException e) {

			throw new PortalBadRequestException("Invalid Json Execption", e);

		}

	}
	
	
	@GetMapping("/Hierarchy/{employeename}")
	@Authenticate
	public EmployeeNode getHierarchy(HttpServletRequest request,@PathVariable(name = "employeename")String employeeName) {
		
		return employeeManagementService.findSuperVisor(employeeName);

	}

}
