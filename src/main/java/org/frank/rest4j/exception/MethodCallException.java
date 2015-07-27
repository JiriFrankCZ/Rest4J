package org.frank.rest4j.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

/**
 * Created by Jiøí on 27. 7. 2015.
 */
public class MethodCallException extends Exception {

    private HttpHeaders httpHeaders;
    private Object httpBody;

    public MethodCallException(HttpStatus expected, HttpStatus returned, HttpHeaders httpHeaders, Object httpBody) {
        super("Method call failed, expected " + expected + ", but got " + returned + ".");
        this.httpHeaders = httpHeaders;
        this.httpBody = httpBody;
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    public Object getHttpBody() {
        return httpBody;
    }
}
