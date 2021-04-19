package com.bjs.webstore.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjs.webstore.domain.Order;
import com.bjs.webstore.domain.repository.OrderRepository;
import com.bjs.webstore.domain.repository.ProductRepository;
import com.bjs.webstore.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService{

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ProductRepository productRepository;

	@Override
	public Long saveOrder(Order order) {
		// @formatter:off
				/**
				 * Change(=decrease) product stock.
				 */
				order.getCart().getCartItems().forEach(item-> {
					productRepository.changeStock(
							item.getProduct().getProductId(), 
							-item.getQuantity());});
				return orderRepository.saveOrder(order);
				// @formatter:on
	}

}
