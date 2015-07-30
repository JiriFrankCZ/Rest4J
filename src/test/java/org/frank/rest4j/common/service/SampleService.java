package org.frank.rest4j.common.service;

import org.frank.rest4j.common.domain.Client;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by Jiøí on 26. 7. 2015.
 */

@Path("/test")
public class SampleService {

	@GET
	@Path("/clients/delete/{id}")
	@Consumes("application/json")
	@Produces("application/json")
	public Response deleteError(@PathParam("id") int id) {
		return Response.status(Response.Status.NOT_FOUND).build();
	}

	@GET
	@Path("/clients/{id}")
	@Consumes("application/json")
	@Produces("application/json")
	public Client view(@PathParam("id") int id) {
		Client client = new Client();
		client.setId(id);
		client.setName("Test client");
		return client;
	}

}
