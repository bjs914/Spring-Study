package com.bjs.webstore.validator;

import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;

@Target( { METHOD, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
/**
 * complile error - until step 2 is done.
 * @author 96
 *
 */
@Constraint(validatedBy = ProductIdValidator.class) // compile error
// until step 2 is done.
@Documented
public @interface ProductId {
	String message() default "{com.bjs.webstore.validator.ProductId.message}";

	Class<?>[] groups() default {};

	public abstract Class<? extends Payload>[] payload() default {};
}
