package com.example.model;

public class PaymentResponse {
	
	private Long id;
	
	private Long loanId;
	
	private double debt;

	public PaymentResponse(Long id, Long loanId, double debt) {
		super();
		this.id = id;
		this.loanId = loanId;
		this.debt = debt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public double getDebt() {
		return debt;
	}

	public void setDebt(double debt) {
		this.debt = debt;
	}
	
}
