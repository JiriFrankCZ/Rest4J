package org.frank.rest4j.logic;

import org.frank.rest4j.fixture.domain.Client;
import org.frank.rest4j.fixture.service.SampleService;
import org.frank.rest4j.fixture.source.SampleInterface1;
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

/**
 * Created by FrankJ on 22.7.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/testContext.xml" })
public class MethodCallTest extends JerseyTest {

    @Autowired
    private SampleInterface1 sampleInterface1;

    @Override
    protected Application configure() {
        forceSet(TestProperties.CONTAINER_PORT, "8080");
        return new ResourceConfig(SampleService.class);
    }


    @Test
    public void testSimpleGETCall(){
        Client client = sampleInterface1.getClient(10);
        Assert.assertNotNull(client);
        Assert.assertEquals(10, client.getId());
    }

}