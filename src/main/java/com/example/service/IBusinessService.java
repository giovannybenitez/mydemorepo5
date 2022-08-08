package com.example.service;

import java.util.Date;
import java.util.List;

import com.example.model.Debt;
import com.example.model.Loan;
import com.example.model.LoanRequest;
import com.example.model.LoanResponse;
import com.example.model.Payment;
import com.example.model.PaymentResponse;
import com.example.model.Target;

public interface IBusinessService {
	
	/**
	 * Retorna una solicitud de prestamo con su respectivo estado.
	 * @param loanRequest Objeto con los parametros (amount, term y userId)
	 * @return Respuesta de una solicitud de prestamo
	 */
	public LoanResponse createLoanRequest(LoanRequest loanRequest);
	
	/**
	 * @param userId
	 */
	public void changeUserTarget(Long userId);
	
	/**
	 * @param target
	 */
	public void changeTargetParams(Target target);

	/**
	 * @param startDate
	 * @param endDate
	 * @param page
	 * @param size
	 * @return
	 */
	public List<Loan> getLoansByDateFilter(Date startDate, Date endDate, int page, int size);

	/**
	 * @param payment
	 * @return
	 */
	public PaymentResponse registerPayment(Payment payment);

	/**
	 * @param loanId
	 * @return
	 */
	public Debt getDebtByLoan(Long loanId);

	/**
	 * @return
	 */
	public Debt getDebtByOpenLoans();

	/**
	 * @param target
	 * @return
	 */
	public Debt getDebtByTarget(String target);

	

}
