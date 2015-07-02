package org.frank.rest4j.provider;

import org.frank.rest4j.provider.fixture.SampleInterface1;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Jiøí on 1. 7. 2015.
 */



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/testContext.xml" })
public class RestInterfaceScannerTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testBaseScanning() throws ClassNotFoundException {
        //new RestInterfacesScanner("org.frank.rest4j.provider.fixture").init();
    }

    @Test
    public void testBaseContainer(){
       applicationContext.getBean("restInterfacesScanner");
        SampleInterface1 sampleInterface1 = applicationContext.getBean(SampleInterface1.class);
        sampleInterface1.test1();
    }
}
