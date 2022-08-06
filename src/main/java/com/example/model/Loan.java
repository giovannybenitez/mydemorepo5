package com.example.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Loan {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long loanId;
	
	private double amount;
	
	private int term;
	
	private double rate;
	
	private Long userId;
	
	private String target;
	
	private Date date;
	
	private String status;
	
	public Loan() {
		
	}

	public Loan(double amount, int term, double rate, Long userId, String target, Date date, String status) {
		super();
		this.amount = amount;
		this.term = term;
		this.rate = rate;
		this.userId = userId;
		this.target = target;
		this.date = date;
		this.status = status;
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

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	

}
