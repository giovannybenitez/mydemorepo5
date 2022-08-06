package com.example.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Target {
	
	@Id @GeneratedValue
	private Long targetId;
	
	private String type;
	
	private int minLoanCount;
	
	private int maxLoanCount;
	
	private double minLoanAllowed;
	
	private double maxLoanAllowed;
	
	private double rate;
	
	private double maxAmount;
	
	public Target() {
	}

	public Target(String type, int minLoanCount, int maxLoanCount, double minLoanAllowed, double maxLoanAllowed, double rate,
			double maxAmount) {
		super();
		this.type = type;
		this.minLoanCount = minLoanCount;
		this.maxLoanCount = maxLoanCount;
		this.minLoanAllowed = minLoanAllowed;
		this.maxLoanAllowed = maxLoanAllowed;
		this.rate = rate;
		this.maxAmount = maxAmount;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getMinLoanCount() {
		return minLoanCount;
	}

	public void setMinLoanCount(int minLoanCount) {
		this.minLoanCount = minLoanCount;
	}

	public int getMaxLoanCount() {
		return maxLoanCount;
	}

	public void setMaxLoanCount(int maxLoanCount) {
		this.maxLoanCount = maxLoanCount;
	}

	public double getMinLoanAllowed() {
		return minLoanAllowed;
	}

	public void setMinLoanAllowed(double minLoanAllowed) {
		this.minLoanAllowed = minLoanAllowed;
	}

	public double getMaxLoanAllowed() {
		return maxLoanAllowed;
	}

	public void setMaxLoanAllowed(double maxLoanAllowed) {
		this.maxLoanAllowed = maxLoanAllowed;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getMaxAmount() {
		return maxAmount;
	}

	public void setMaxAmount(double maxAmount) {
		this.maxAmount = maxAmount;
	}
}
