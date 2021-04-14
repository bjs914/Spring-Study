package com.bjs.webstore.domain.repository;

import com.bjs.webstore.domain.Cart;
import com.bjs.webstore.dto.CartDto;

public interface CartRepository {
	//가상메소드
	void create(CartDto cartDto);
	Cart read(String id);
	void update(String id, CartDto cartDto);
	void delete(String id);
	void addItem(String cartId, String productId);
	void removeItem(String cartId, String productId);
}
