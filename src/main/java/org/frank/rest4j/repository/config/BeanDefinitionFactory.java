package org.frank.rest4j.repository.config;

import org.frank.rest4j.repository.GenericMethodInvokingFactoryBean;
import org.frank.rest4j.util.CommonsUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;

import java.util.Set;

/**
 * Created by FrankJ on 31.7.2015.
 */
public class BeanDefinitionFactory {
	protected final org.slf4j.Logger Logger = LoggerFactory.getLogger(BeanDefinitionFactory.class);

	private Class factoryClass;
	private String factoryMethodName;

	public BeanDefinitionFactory(Class factoryClass, String factoryMethodName) {
		this.factoryClass = factoryClass;
		this.factoryMethodName = factoryMethodName;
	}

	/**
	 * Creates bean definitions and assign them to the context
	 *
	 * @param beanDefinitionRegistry
	 * @param interfaces
	 */
	public void registerInterfaces(BeanDefinitionRegistry beanDefinitionRegistry, Set<String> interfaces) {
		if (interfaces != null && interfaces.size() > 0) {

			for (String interfaceName : interfaces) {
				BeanDefinition beanDefinition = createBeanDefinition(interfaceName);

				if (beanDefinition != null) {
					beanDefinitionRegistry.registerBeanDefinition(
							CommonsUtil.getClassNameFromFQN(interfaceName),
							beanDefinition
					);
				}
			}
		}
	}

	/**
	 * Creates definition for factory GenericMethodInvokingFactoryBean which then creates dynamic proxy
	 * based on interfaces provided as param.
	 *
	 * @param interfaceName
	 * @return Factory bean definition
	 */
	private BeanDefinition createBeanDefinition(String interfaceName) {

		BeanDefinition beanDefinition = null;
		try {
			Class interfaceClass = Class.forName(interfaceName);

			beanDefinition = BeanDefinitionBuilder
					.rootBeanDefinition(GenericMethodInvokingFactoryBean.class)
					.addPropertyValue("targetClass", factoryClass)
					.addPropertyValue("targetMethod", factoryMethodName)
					.addPropertyValue("targetType", interfaceClass)
					.addPropertyValue("arguments", interfaceClass)
					.setLazyInit(false)
					.getBeanDefinition();

		} catch (ClassNotFoundException e) {
			Logger.error("Interface couldn´t be resolved for {}.", interfaceName);
		}

		return beanDefinition;
	}
}
