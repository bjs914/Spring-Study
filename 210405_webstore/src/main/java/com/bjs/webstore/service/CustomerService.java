package com.bjs.webstore.service;

import java.util.List;

import com.bjs.webstore.domain.Customers;

public interface CustomerService {
	List<Customers> getAllCustomers();
	void addCustomer(Customers customer);
}