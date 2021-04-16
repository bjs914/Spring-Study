package com.bjs.webstore.domain.repository;

import com.bjs.webstore.domain.Customer;
import com.bjs.webstore.domain.Order;

public interface OrderRepository {
	long saveOrder(Order order);

	long saveCustomer(Customer customer);
}
