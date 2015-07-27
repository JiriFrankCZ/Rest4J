package org.frank.rest4j.annotation;

import org.frank.rest4j.constant.Format;

import java.lang.annotation.*;

/**
 * Created by Ji�� on 2. 7. 2015.
 */

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
public @interface Client {
    String value();
    Format format() default Format.JSON;
}
