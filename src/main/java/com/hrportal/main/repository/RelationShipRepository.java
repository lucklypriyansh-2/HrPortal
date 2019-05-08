package com.hrportal.main.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.hrportal.main.model.RelationShip;

@Repository
public interface RelationShipRepository extends JpaRepository<RelationShip, Integer> {

	@Query(value = "SELECT SUPERVISOR FROM  RELATIONSHIP MINUS SELECT EMPLOYEE FROM  RELATIONSHIP   ", nativeQuery = true)
	List<String> getallRoots();

	
	List<RelationShip> findBySupervisor(String supervisor);
	
	List<RelationShip> findByEmployee(String employee);

}
