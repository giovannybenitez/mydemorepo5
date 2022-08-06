package com.example.model;

public class LoanRequest {
	
	private double amount;
	
	private int term;
	
	private Long userId;
	
	public LoanRequest() {
		
	}

	public LoanRequest(double amount, int term, Long userId) {
		super();
		this.amount = amount;
		this.term = term;
		this.userId = userId;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	

}
