package com.bjs.webstore.domain.repository;

import java.util.List;

import com.bjs.webstore.domain.Customer;
import com.bjs.webstore.domain.Customers;


public interface CustomerRepository {
	List<Customers> getAllCustomers();
	void addCustomer(Customers customer);
	List<Customer> getAllCustomer();
}
