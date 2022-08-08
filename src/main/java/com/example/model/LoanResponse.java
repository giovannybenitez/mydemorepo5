package com.example.model;

public class LoanResponse {
	
	private Long loanId;
	
	private double installment;
	
	private String message;
	
	public LoanResponse(Long loanId, double installment) {
		super();
		this.loanId = loanId;
		this.installment = installment;
	}

	public LoanResponse(String message) {
		super();
		this.message = message;
	}

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public double getInstallment() {
		return installment;
	}

	public void setInstallment(double installment) {
		this.installment = installment;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
