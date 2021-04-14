package com.bjs.webstore.domain.repository;

import java.util.List;
import com.bjs.webstore.domain.Customer;


public interface CustomerRepository {
	List<Customer> getAllCustomers();
	void addCustomer(Customer customer);
}
