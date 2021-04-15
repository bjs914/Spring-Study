package com.bjs.webstore.exception;

public class InvalidCartException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6098769266964331038L;
	private String cartId;
	
	public InvalidCartException(String cartId2) {

	}
	public String getCartId() {
		return cartId;
	}
	public void setCartId(String cartId) {
		this.cartId = cartId;
	}	
}
