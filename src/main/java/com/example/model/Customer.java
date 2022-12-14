package com.example.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Customer {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customerId;
	
	private String firstName;
	
	private String lastName;
	
	private String target;
	
	
	public Customer() {
	}
	
	
	public Customer(String firstName, String lastName, String target) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.target = target;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

}
