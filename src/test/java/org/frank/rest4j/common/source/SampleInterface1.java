package org.frank.rest4j.common.source;

import org.frank.rest4j.annotation.Action;
import org.frank.rest4j.annotation.Client;
import org.frank.rest4j.annotation.Method;
import org.frank.rest4j.annotation.Param;
import org.frank.rest4j.common.domain.User;
import org.springframework.http.HttpMethod;

import java.util.concurrent.Future;

/**
 * Created by Ji�� on 1. 7. 2015.
 */
@Client("http://localhost:8080/test")
public interface SampleInterface1 {

	@Method(HttpMethod.GET)
	@Action(value = "/clients/{id}", async = true)
	Future<User> get(@Param("id") int id);

	@Action(value = "/clients/delete/{id}")
	void delete(@Param("id") int id);
}
