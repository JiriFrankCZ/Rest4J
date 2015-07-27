package org.frank.rest4j.fixture.source;

import org.frank.rest4j.annotation.Action;
import org.frank.rest4j.annotation.Client;
import org.frank.rest4j.annotation.Param;
import org.springframework.http.HttpStatus;

/**
 * Created by Jiøí on 1. 7. 2015.
 */
@Client("http://localhost:8080/test")
public interface SampleInterface1 {

    @Action(value = "/clients/{id}", successStatus = HttpStatus.OK)
    org.frank.rest4j.fixture.domain.Client getClient(@Param("id") int id);
}
