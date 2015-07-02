package org.frank.rest4j.provider;

import org.frank.rest4j.annotation.RestClient;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

/**
 * Created by Jiøí on 2. 7. 2015.
 */
public class ClassPathScanningRestInterfaceProvider extends ClassPathScanningCandidateComponentProvider {

    public ClassPathScanningRestInterfaceProvider() {
        super(false);
        addIncludeFilter(new AnnotationTypeFilter(RestClient.class, false));
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface();
    }
}