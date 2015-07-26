package org.frank.rest4j.repository.config.xml;

import org.frank.rest4j.fixture.source.SampleInterface1;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/testContext.xml" })
public class SpringContainerXMLTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private SampleInterface1 sampleInterface1;

    @Test
    public void testBaseContainer() {
        Object nameInjectedBean = applicationContext.getBean("SampleInterface1");
        Assert.notNull(nameInjectedBean, "Injection by name of proxy was not successfull.");

        Object typeInjectedBean = applicationContext.getBeansOfType(SampleInterface1.class);
        Assert.notNull(typeInjectedBean, "Injection by type of proxy was not successfull.");

        Assert.notNull(sampleInterface1, "Autowired by type of proxy was not successfull.");
    }
}
