package org.frank.rest4j.fixture.service;

import org.frank.rest4j.annotation.Action;
import org.frank.rest4j.annotation.Param;
import org.frank.rest4j.fixture.domain.Client;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by Jiøí on 26. 7. 2015.
 */

@Path("test")
public class SampleService {

    @GET
    @Path("/clients/{id}")
    public Client clients(int id) {
        Client client = new Client();
        client.setId(id);
        client.setName("Test client");
        return client;
    }

}
