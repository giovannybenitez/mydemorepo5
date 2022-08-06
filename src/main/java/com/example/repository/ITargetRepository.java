package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.model.Target;

@Repository
public interface ITargetRepository extends JpaRepository<Target, Long> {
	
	@Query(value = "SELECT * from target where type =:type", nativeQuery = true)
	public Target findByType(@Param("type") String type);

}
	