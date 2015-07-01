package org.frank.rest4j.provider;

import org.junit.Test;

/**
 * Created by Jiøí on 1. 7. 2015.
 */


public class RestInterfaceScannerTest {

    @Test
    public void testBaseScanning(){
        new RestInterfacesScanner("org.frank.rest4j.provider.fixture").init();
    }
}
