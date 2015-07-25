package org.frank.rest4j.annotation;

import org.frank.rest4j.constant.Format;

import java.lang.annotation.*;

/**
 * Created by Jiøí on 2. 7. 2015.
 */

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.TYPE)
public @interface RestClient {
    String url();
    Format format() default Format.JSON;
}
