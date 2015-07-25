package org.frank.rest4j.logic;

import org.frank.rest4j.repository.fixture.domain.Client;
import org.frank.rest4j.repository.fixture.source.SampleInterface1;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by FrankJ on 22.7.2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/testContext.xml" })
public class MethodCallTest {

    @Autowired
    private SampleInterface1 sampleInterface1;

    @Test
    public void testSimpleGETCall(){
        Client client = sampleInterface1.getClient(10);
        Assert.assertNotNull(client);
        Assert.assertEquals(10, client.getId());
    }

}