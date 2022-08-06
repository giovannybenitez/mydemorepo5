package com.example.service;

import java.util.Date;
import java.util.List;

import com.example.model.Loan;
import com.example.model.LoanRequest;
import com.example.model.LoanResponse;
import com.example.model.Payment;
import com.example.model.PaymentResponse;
import com.example.model.Target;

public interface IBusinessService {

	public LoanResponse createLoanRequest(LoanRequest loanRequest);

	public List<Loan> getLoansByDateFilter(Date startDate, Date endDate, int page, int size);

	public PaymentResponse registerPayment(Payment payment);

	public Target getTargetByType(String type);

	public void changeUserTarget(Long userId);

}
