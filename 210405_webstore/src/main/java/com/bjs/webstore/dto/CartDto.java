package com.bjs.webstore.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CartDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1339965056458894858L;
	private String id;
	private List<CartItemDto> cartItems;
	
	public CartDto() {
	} // 기본 생성자

	public CartDto(String id) {
		this.id = id;
		cartItems = new ArrayList<>();
	} //	두 필드에대한 getters, setters 생성
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<CartItemDto> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItemDto> cartItems) {
		this.cartItems = cartItems;
	}

	public void addCartItem(CartItemDto cartItemDto) {
		this.cartItems.add(cartItemDto);
	}

}
