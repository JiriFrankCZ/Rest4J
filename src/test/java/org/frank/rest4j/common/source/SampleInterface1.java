package org.frank.rest4j.common.source;

import org.frank.rest4j.annotation.Action;
import org.frank.rest4j.annotation.Client;
import org.frank.rest4j.annotation.Param;
import org.frank.rest4j.annotation.methods.PUT;
import org.springframework.http.HttpStatus;

/**
 * Created by Jiøí on 1. 7. 2015.
 */
@Client("http://localhost:8080/test")
public interface SampleInterface1 {

	@PUT
	@Action(value = "/clients/{id}", successStatus = HttpStatus.OK)
	org.frank.rest4j.common.domain.Client get(@Param("id") int id);

	@Action(value = "/clients/delete/{id}", successStatus = HttpStatus.OK)
	void delete(@Param("id") int id);
}
