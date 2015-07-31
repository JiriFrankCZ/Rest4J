package org.frank.rest4j.context;

import org.frank.rest4j.repository.config.annotation.RestRepositoryRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(value = RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
@Import(RestRepositoryRegistrar.class)
public @interface EnableRestProxies {
	String value();
}