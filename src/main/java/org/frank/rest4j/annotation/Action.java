package org.frank.rest4j.annotation;

import org.springframework.http.HttpMethod;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.METHOD)
public @interface Action {
    String urlFragment();
    HttpMethod method() default HttpMethod.GET;
}