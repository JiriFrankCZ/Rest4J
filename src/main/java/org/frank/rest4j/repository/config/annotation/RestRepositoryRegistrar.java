package org.frank.rest4j.repository.config.annotation;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Created by FrankJ on 31.7.2015.
 */
public class RestRepositoryRegistrar implements ImportBeanDefinitionRegistrar, ApplicationContextInitializer {
	public void initialize(ConfigurableApplicationContext applicationContext) {
		System.out.println("asd");
	}


	public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {


		System.out.println("asd");
	}

}
