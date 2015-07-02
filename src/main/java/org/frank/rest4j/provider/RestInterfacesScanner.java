package org.frank.rest4j.provider;

import org.frank.rest4j.annotation.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import javax.annotation.PostConstruct;
import java.util.Set;


/**
 * Created by Jiøí on 1. 7. 2015.
 */
public class RestInterfacesScanner {

    private final Logger Logger = LoggerFactory.getLogger(RestInterfacesScanner.class);

    private String basePackage;

    public RestInterfacesScanner(String basePackage) {
        this.basePackage = basePackage;
    }

    @PostConstruct
    public void init(){
        Logger.debug("Started initializing of scanner bean.");

        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningRestInterfaceProvider();

System.out.println("Start");
        Set<BeanDefinition> components = provider.findCandidateComponents(basePackage);
        for (BeanDefinition component : components) {

            System.out.println(component.getBeanClassName());
            Logger.debug(component.getBeanClassName());
        }
    }
}
