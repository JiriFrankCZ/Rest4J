package org.frank.rest4j.repository;

import org.frank.rest4j.provider.ClassPathScanningRestInterfaceProvider;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;

import java.util.HashSet;
import java.util.Set;

/**
 *
 */
public class ComponentsScanner {
    private Class annotationFilter;

    private final org.slf4j.Logger Logger = LoggerFactory.getLogger(ComponentsScanner.class);

    private String basePackage;

    public ComponentsScanner(Class annotationFilter) {
        this.annotationFilter = annotationFilter;
    }

    public Set<String> scan(String basePackage){
        Logger.debug("Started initializing of scanner bean.");

        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningRestInterfaceProvider(annotationFilter);
        Set<BeanDefinition> beanDefinitions = provider.findCandidateComponents(basePackage);

        Set<String> resolvedInterfaceList = new HashSet<String>(beanDefinitions.size());

        for (BeanDefinition beanDefinition : beanDefinitions) {
            String className = beanDefinition.getBeanClassName();

            Logger.debug("Resolved interface " + className);
            resolvedInterfaceList.add(className);
        }

        return resolvedInterfaceList;
    }
}
