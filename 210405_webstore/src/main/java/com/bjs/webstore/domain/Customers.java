package com.bjs.webstore.domain;

import java.io.Serializable;

public class Customers implements Serializable{
	
	/**
	 * 
	 */
	public static final long serialVersionUID = 5580816632339253958L;
	public String customerId;
	public String name;
	public String address;
	public int noOfOrdersMade;
	
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getNoOfOrdersMade() {
		return noOfOrdersMade;
	}
	public void setNoOfOrdersMade(int noOfOrdersMade) {
		this.noOfOrdersMade = noOfOrdersMade;
	}
	
	
}
