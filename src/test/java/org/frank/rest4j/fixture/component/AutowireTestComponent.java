package org.frank.rest4j.fixture.component;


import org.frank.rest4j.fixture.source.SampleInterface1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by FrankJ on 20.7.2015.
 */
@Component
public class AutowireTestComponent {

    @Autowired
    private SampleInterface1 sampleInterface1;

}
