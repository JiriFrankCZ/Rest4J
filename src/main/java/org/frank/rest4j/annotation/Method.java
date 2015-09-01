package org.frank.rest4j.annotation;

import org.springframework.http.HttpMethod;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Method {
	HttpMethod value();
}
