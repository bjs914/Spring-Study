package com.bjs.webstore.service;

import java.util.List;

import com.bjs.webstore.domain.Product;

public interface ProductService {
	void updateAllStock();
	List<Product> getAllProducts();
	List<Product> getProductsByCategory(String category);
}
