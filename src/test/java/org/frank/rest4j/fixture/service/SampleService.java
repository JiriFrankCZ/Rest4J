package org.frank.rest4j.fixture.service;

import org.frank.rest4j.annotation.Action;
import org.frank.rest4j.annotation.Param;
import org.frank.rest4j.fixture.domain.Client;

import javax.ws.rs.*;

/**
 * Created by Jiøí on 26. 7. 2015.
 */

@Path("/test")
public class SampleService {

    @GET
    @Path("/clients/{id}")
    @Consumes("application/json")
    @Produces("application/json")
    public Client clients(@PathParam("id") int id) {
        Client client = new Client();
        client.setId(id);
        client.setName("Test client");
        return client;
    }

}
