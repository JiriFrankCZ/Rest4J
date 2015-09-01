package org.frank.rest4j.repository.config.annotation;

import org.frank.rest4j.constant.Constants;
import org.frank.rest4j.context.EnableRestProxies;
import org.frank.rest4j.repository.ComponentsScanner;
import org.frank.rest4j.repository.config.BeanDefinitionFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Set;

/**
 * Created by FrankJ on 31.7.2015.
 */
public class RestRepositoryRegistrar implements ImportBeanDefinitionRegistrar{
	private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(RestRepositoryRegistrar.class);

	private static final String SOURCE_ATTRIBUTE_NAME = "value";

	private ComponentsScanner componentsScanner;
	private BeanDefinitionFactory beanDefinitionFactory;

	public void init() {
		componentsScanner = new ComponentsScanner(Constants.DEFAULT_ANNOTATION_FILTER);
		beanDefinitionFactory = new BeanDefinitionFactory(Constants.FACTORY_CLASS, Constants.FACTORY_METHOD_NAME);
	}

	public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
		init();

		final Map<String, Object> annotationAttributes = annotationMetadata.getAnnotationAttributes(EnableRestProxies.class.getCanonicalName());
		String basePackage = (String) annotationAttributes.get(SOURCE_ATTRIBUTE_NAME);

		Assert.notNull(basePackage, "Base package has to be defined in every case.");
		Logger.info("Base package was set to {} and will be scaned for RestRespository source.", basePackage);

		Set<String> interfaces = componentsScanner.scan(basePackage);

		beanDefinitionFactory.registerInterfaces(beanDefinitionRegistry, interfaces);
	}

}
