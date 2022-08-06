package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.model.Payment;

@Repository
public interface IPaymentRepository extends JpaRepository<Payment, Long> {
	
	@Query(value = "SELECT * FROM payment WHERE loan_id = :loanId", nativeQuery = true)
	public List<Payment> findByLoan(@Param("loanId") Long loanId);

}
	