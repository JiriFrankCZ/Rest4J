package org.frank.rest4j.context;

import org.frank.rest4j.provider.RestInterfacesScanner;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
@Import(RestInterfacesScanner.class)
public @interface EnableRestProxies {
    String name();
}