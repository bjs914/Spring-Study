package com.bjs.webstore.domain.repository;

import java.util.List;

import com.bjs.webstore.domain.Product;

public interface ProductRepository {	//repository : table
	List<Product> getAllProducts();
	void updateStock(String productId, long noOfUnits);
	List<Product> getProductsByCategory(String category);
	
	
}
