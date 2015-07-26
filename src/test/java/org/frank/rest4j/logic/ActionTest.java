package org.frank.rest4j.logic;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by FrankJ on 22.7.2015.
 */
public class ActionTest {

    @Test
    public void testRequestUrlGeneration(){
        final String serverUrl = "http://www.test.cz";
        final String fragmentUrl = "/clients/get/{id}/sort/{sort}";
        final String expectedUrl = serverUrl + fragmentUrl;

        RestMethod restMethod = new RestMethod();
        restMethod.setUrlFragment(fragmentUrl);

        String result = restMethod.getLink(serverUrl);

        Assert.assertEquals("Method url generation failed", expectedUrl, result);
    }
}
