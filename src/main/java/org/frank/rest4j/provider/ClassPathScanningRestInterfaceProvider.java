package org.frank.rest4j.provider;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

/**
 * Created by Jiøí on 2. 7. 2015.
 */
public class ClassPathScanningRestInterfaceProvider extends ClassPathScanningCandidateComponentProvider {

    public ClassPathScanningRestInterfaceProvider(Class annotation) {
        super(false);
        addIncludeFilter(new AnnotationTypeFilter(annotation, false));
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        return beanDefinition.getMetadata().isInterface();
    }
}