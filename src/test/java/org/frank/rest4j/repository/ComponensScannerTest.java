package org.frank.rest4j.repository;

import org.frank.rest4j.fixture.source.SampleInterface1;
import org.junit.Assert;
import org.frank.rest4j.annotation.Client;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Set;

/**
 * Created by Jiøí on 20. 7. 2015.
 */
public class ComponensScannerTest {

    private static final String PACKAGE_TO_TEST = "org.frank.rest4j.fixture.source";

    private static ComponentsScanner componentsScanner;

    @BeforeClass
    public static void setUp(){
        componentsScanner = new ComponentsScanner(Client.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullPackage(){
        componentsScanner.scan(null);
    }

    @Test
    public void testFakePackage(){
        Set<String> resolvedClasses =  componentsScanner.scan("test.test");

        Assert.assertEquals(0, resolvedClasses.size());
    }

    @Test
    public void testPackage(){
        Set<String> resolvedClasses =  componentsScanner.scan(PACKAGE_TO_TEST);

        Assert.assertEquals(1, resolvedClasses.size());
        Assert.assertEquals(SampleInterface1.class.getCanonicalName(), resolvedClasses.toArray()[0]);
    }
}
