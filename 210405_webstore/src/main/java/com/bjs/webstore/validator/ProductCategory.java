package com.bjs.webstore.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
/**
 * complile error - until step 2 is done.
 * @author 96
 *
 */
@Constraint(validatedBy = ProductCategoryValidator.class) // compile error
// until step 2 is done.
@Documented
public @interface ProductCategory {
	String message() default "{com.bjs.webstore.validator.ProductCategory.message}";

	Class<?>[] groups() default {};

	public abstract Class<? extends Payload>[]
			payload() default {};
}
