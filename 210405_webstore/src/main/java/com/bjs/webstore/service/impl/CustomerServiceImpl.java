package com.bjs.webstore.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjs.webstore.domain.Customer;
import com.bjs.webstore.domain.Customers;
import com.bjs.webstore.domain.repository.CustomerRepository;
import com.bjs.webstore.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService{
	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public List<Customers> getAllCustomers() {
		return customerRepository.getAllCustomers();
	}

	@Override
	public void addCustomer(Customers customer) {
		customerRepository.addCustomer(customer);
	}

	@Override
	public List<Customer> getAllCustomer() {
		return customerRepository.getAllCustomer();
	}

	
	
}
