package com.example.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Payment {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long paymentId;
	
	private Long loanId;
	
	private double amount;
	
	private Date date;
	

	public Payment() {
		// TODO Auto-generated constructor stub
	}

	public Payment(Long loanId, double amount, Date date) {
		super();
		this.loanId = loanId;
		this.amount = amount;
		this.date = date;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	

}
