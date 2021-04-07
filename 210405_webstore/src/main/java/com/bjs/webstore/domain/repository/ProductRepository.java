package com.bjs.webstore.domain.repository;

import java.util.List;
import java.util.Map;

import com.bjs.webstore.domain.Product;

public interface ProductRepository {	//repository : table
	List<Product> getAllProducts();
	void updateStock(String productId, long noOfUnits);
	List<Product> getProductsByCategory(String category);
	List<Product> getProductsByFilter(
			Map<String, List<String>> filterParams);
	Product getProductById(String productID);
	List<Product> getProdsByMultiFilter(
	String productCategory,
	Map<String, String> price, String brand);
	
}
