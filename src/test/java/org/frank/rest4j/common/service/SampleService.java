package org.frank.rest4j.common.service;

import org.frank.rest4j.common.domain.User;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

/**
 * Created by Ji�� on 26. 7. 2015.
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
	public User view(@PathParam("id") int id) {
		User user = new User();
		user.setId(id);
		user.setName("Test user");
		return user;
	}

}
