package org.frank.rest4j.repository.fixture.source;

import org.frank.rest4j.annotation.RestAction;
import org.frank.rest4j.annotation.RestClient;
import org.frank.rest4j.annotation.RestParam;
import org.frank.rest4j.repository.fixture.domain.Client;

/**
 * Created by Jiøí on 1. 7. 2015.
 */
@RestClient(url = "http://localhost:8080/test")
public interface SampleInterface1 {

    @RestAction(urlFragment = "/clients/{id}")
    Client getClient(@RestParam("id") int id);
}
