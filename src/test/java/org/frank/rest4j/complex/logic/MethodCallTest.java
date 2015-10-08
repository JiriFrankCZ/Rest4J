package org.frank.rest4j.complex.logic;

import org.frank.rest4j.common.domain.User;
import org.frank.rest4j.common.service.SampleService;
import org.frank.rest4j.common.source.SampleInterface1;
import org.frank.rest4j.exception.MethodCallException;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.core.Application;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by FrankJ on 22.7.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/testContext.xml"})
public class MethodCallTest extends JerseyTest {

	@Autowired
	private SampleInterface1 sampleInterface1;

	@Override
	protected Application configure() {
		forceSet(TestProperties.CONTAINER_PORT, "8080");
		return new ResourceConfig(SampleService.class);
	}

	@Test
	public void testGetCallWithParam() throws ExecutionException, InterruptedException {
		final int clientId = 10;

		Future<User> user = sampleInterface1.get(clientId);

		Assert.assertNotNull(user);
		Assert.assertEquals(clientId, user.get().getId());
	}

	@Test(expected = MethodCallException.class)
	public void testErroredGetCall() {
		final int clientId = 10;

		sampleInterface1.delete(clientId);
	}
}