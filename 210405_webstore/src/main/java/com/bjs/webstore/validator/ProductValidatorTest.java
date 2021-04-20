package com.bjs.webstore.validator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;

import com.bjs.webstore.config.WebApplicationContextConfig;
import com.bjs.webstore.domain.Product;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebApplicationContextConfig.class)
@WebAppConfiguration
public class ProductValidatorTest {
	@Autowired
	private ProductValidator productValidator;

	@Test
	public void product_without_UnitPrice_should_be_invalid() {
		// Arrange
		Product product = new Product();
		BindException bindException = new BindException(product, "product");
		// Act
		ValidationUtils.invokeValidator(productValidator, product, bindException);
		// @formatter:off
		// Assert
		Assert.assertEquals(3, bindException.getErrorCount());
		String result = bindException.getLocalizedMessage();
		Assert.assertTrue(bindException.getLocalizedMessage().contains("같은 ID를 가지는 상품이 존재함"));
		// @formatter:on
	}

	@Test
	public void product_with_existing_productId_invalid() {
		// Arrange
		Product product = new Product("P1234", "iPhone 5s", 500,1000);
		product.setCategory("Tablet");
		BindException bindException = new BindException(product, "product");
		// Act
		ValidationUtils.invokeValidator(productValidator, product, bindException);
		// Assert
		Assert.assertEquals(1, bindException.getErrorCount());
		String resultString = bindException.getLocalizedMessage();
		Assert.assertTrue(bindException.getLocalizedMessage().contains("상품 ID 오류. 형식: P로 시작하는 숫자"));
	}

	@Test
	public void a_valid_product_should_not_get_any_error_during_validation() {
		// Arrange
		Product product = new Product("P9876", "iPhone 5s", 500,1000);
		product.setCategory("Tablet");
		BindException bindException = new BindException(product, "product");
		// Act
		ValidationUtils.invokeValidator(productValidator, product, bindException);
		// Assert
		Assert.assertEquals(0, bindException.getErrorCount());
		bindException.getBindingResult();
	}
}
