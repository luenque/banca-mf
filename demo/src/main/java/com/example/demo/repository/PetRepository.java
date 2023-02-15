package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.entity.PetEntity;

public interface PetRepository extends JpaRepository<PetEntity, Integer> {
	// Look for an employee with the provided taxIdNumber
	@Query("select c from PetEntity c where lower(c.name) like lower(CONCAT('%',:name,'%'))")
	List<PetEntity> filterByName(@Param("name") String name);
}
