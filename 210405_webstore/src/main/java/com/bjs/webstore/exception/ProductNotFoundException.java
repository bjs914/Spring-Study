package com.bjs.webstore.exception;

public class ProductNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4673619171955350832L;
	private String productId;

	public ProductNotFoundException(String productId) {
		this.productId = productId;
	}

	public String getProductId() {
		return productId;
	}
}
