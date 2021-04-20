package com.bjs.webstore.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.bjs.webstore.domain.Product;

@Component
public class ProductImageValidator implements Validator{

	private long allowedSize=3 * 1024 * 1024;

	public long getAllowedSize() {
		return allowedSize;
	}

	public void setAllowedSize(long allowedSize) {
		this.allowedSize = allowedSize;
	}
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Product.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Product product = (Product) target;
		if (product == null || product.getProductImage() == null) {
			return;
		}
		if (product.getProductImage() != null &&	
			product.getProductImage().getSize() > allowedSize) {//210420 null&&까지 추가됨
			errors.rejectValue("productImage", "com.bjs.webstore.validator.ImageSize.message");
		}
		
	}
}
