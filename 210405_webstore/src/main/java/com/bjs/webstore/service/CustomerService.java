package com.bjs.webstore.service;

import java.util.List;

import com.bjs.webstore.domain.Customer;

public interface CustomerService {
	List<Customer> getAllCustomers();
	void addCustomer(Customer customer);
}