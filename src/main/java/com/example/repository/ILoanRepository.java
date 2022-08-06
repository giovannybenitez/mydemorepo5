package com.example.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.model.Loan;

@Repository
public interface ILoanRepository extends PagingAndSortingRepository<Loan, Long> {
	
	@Query(value = "SELECT * FROM loan WHERE date BETWEEN :startDate and :endDate", nativeQuery = true)
	public List<Loan> findByDateFilter(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
	
	@Query(value = "SELECT * FROM loan WHERE date BETWEEN :startDate and :endDate", nativeQuery = true)
	public List<Loan> findByDateFilter(@Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);
	
	@Query(value = "SELECT * FROM loan WHERE user_id = :userId", nativeQuery = true)
	public List<Loan> findByUser(@Param("userId") Long userId);
	

}
	