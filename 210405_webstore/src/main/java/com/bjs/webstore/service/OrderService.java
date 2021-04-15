package com.bjs.webstore.service;

import com.bjs.webstore.domain.Order;

public interface OrderService {
	Long saveOrder(Order order);
}
