package org.frank.rest4j.context;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
//@Import()
public @interface EnableRestProxies {
    String name();
}