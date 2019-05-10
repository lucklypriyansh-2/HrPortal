package com.hrportal.main.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Stack;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;
import com.hrportal.main.exception.PortalBadRequestException;
import com.hrportal.main.exception.PortalException;
import com.hrportal.main.model.RelationShip;
import com.hrportal.main.repository.RelationShipRepository;

@Component
public class TreeService {

	@Autowired
	RelationShipRepository relationShipRepository;

	RelationShip randomEdge;

	@Transactional
	public void initTree(JsonNode jsonNode) {

		relationShipRepository.deleteAll();
		saveEdgeList(jsonNode);

	}

	/**
	 * Save edge list from json node
	 * 
	 * @param jsonNodes
	 * @return List<Edge>
	 */
	private void saveEdgeList(JsonNode jsonNodes) {

		// Can add logic here if it is required that same child cannot be under multiple parent
		jsonNodes.fieldNames().forEachRemaining(fieldName -> {
			JsonNode fieldValue = jsonNodes.path(fieldName);
			if (fieldValue.isTextual()) {
				RelationShip edge = new RelationShip();
				edge.setEmployee(fieldName);
				edge.setSupervisor(fieldValue.textValue());
				randomEdge = edge;
				relationShipRepository.save(edge);
			} else if (fieldValue.isArray()) {
				// Same child multiple parents

				throw new PortalBadRequestException("Employee " + fieldName
						+ " has multiple Supervisor" + fieldValue.toString());

			}else
			{
				throw new PortalBadRequestException("Key value pair is not String ");
			}

		});

	}

	/**
	 * detect loop in tree using recursive
	 * 
	 * @param employee
	 */
	public void detectLoop() {

		detectLoop(randomEdge.getEmployee(), new Stack<>());
	}

	/**
	 * Recursive function to use detectLoop will traverse upward from employee to supervisor
	 * 
	 * @param employee
	 * @param stack
	 */
	public void detectLoop(String employee, Stack stack) {
		stack.push(employee);
		relationShipRepository.findByEmployee(employee).forEach(edge -> {
			if (stack.contains(edge.getSupervisor())) {
				relationShipRepository.deleteAll();
				throw new PortalException("Cycle detected between " + printCycleStack(
						stack.subList(stack.indexOf(edge.getSupervisor()), stack.size())));
			}
			detectLoop(edge.getSupervisor(), stack);
		});
		stack.pop();
	}

	/**
	 * 1.get all roots 2 multiple roots (Top level supervisor) throw error 3. no roots (no top level
	 * supervisor) detect loop 4. Generate tree from root(Top supervisor) to leaf (Employee) and
	 * detect loop if any
	 * 
	 * @return LinkedHashMap<String, Object>
	 */
	public LinkedHashMap<String, Object> generateTree() {

		List<String> roots = relationShipRepository.getallRoots();

		if (roots.size() > 1) {
			throw new PortalException(
					"Error multiple roots in request json Roots[ " + roots.toString() + "]");
		}
		if (roots.size() < 1) {
			detectLoop();
		}

		LinkedHashMap<String, Object> returnMap = new LinkedHashMap<>();

		returnMap.put(roots.get(0), generateTree(roots.get(0), new Stack<>()));
		return returnMap;
	}

	/**
	 * Recurivse function to generate tree 1 Assumption The child can have multiple parent hence i
	 * used list
	 *
	 * @param node<blockquote>Start
	 *                                  node</blockquote>
	 * @param pathstack
	 *                                  <blockquote> Keeps data of recurison stack</blockquote>
	 * @return LinkedHashMap<String, Object>
	 */
	private LinkedHashMap<String, Object> generateTree(String node, Stack<String> pathstack) {

		pathstack.push(node);
		LinkedHashMap<String, Object> returnMap = new LinkedHashMap<>();

		// List<LinkedHashMap<String, Object>> listOfMap = new ArrayList<>();
		relationShipRepository.findBySupervisor(node).forEach(edge -> {

			if (pathstack.contains(edge.getEmployee())) {
				relationShipRepository.deleteAll();
				throw new PortalException("Cycle detected between " + printCycleStack(pathstack
						.subList(pathstack.indexOf(edge.getEmployee()), pathstack.size())));
			}

			returnMap.put(edge.getEmployee(), generateTree(edge.getEmployee(), pathstack));
			// listOfMap.add(generateTree(edge.getEmployee(), pathstack));
		});

		// returnMap.put(node, listOfMap);
		pathstack.pop();
		return returnMap;
	}

	private String printCycleStack(List<String> recstack) {
		String recstackstring = "";
		for (String elem : recstack) {
			recstackstring += "-->" + elem;
		}
		return recstackstring;
	}

}
