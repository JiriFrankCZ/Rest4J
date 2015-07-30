package org.frank.rest4j.annotation;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.METHOD)
public @interface Action {
	String value();

	HttpStatus successStatus();

	HttpMethod method() default HttpMethod.GET;
}