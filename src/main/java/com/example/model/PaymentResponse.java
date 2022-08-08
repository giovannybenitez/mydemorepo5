package com.example.model;

public class PaymentResponse {
	
	private Long id;
	
	private Long loanId;
	
	private double debt;
	
	private String errorCode;
	
	private String errorMessage;

	public PaymentResponse(Long id, Long loanId, double debt) {
		super();
		this.id = id;
		this.loanId = loanId;
		this.debt = debt;
	}
	
	public PaymentResponse(Long loanId) {
		super();
		this.loanId = loanId;
	}
	
	public PaymentResponse(String errorCode, String errorMessage) {
		super();
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
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

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
