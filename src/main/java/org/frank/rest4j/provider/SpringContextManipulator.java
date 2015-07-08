package org.frank.rest4j.provider;

import org.frank.rest4j.general.RestClientProxy;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Jiøí on 2. 7. 2015.
 */
@Component
public class SpringContextManipulator implements BeanFactoryPostProcessor {

    private ConfigurableListableBeanFactory configurableListableBeanFactory;

    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        this.configurableListableBeanFactory = configurableListableBeanFactory;
    }

    public void addServiceToContext(String name, RestClientProxy serviceClassInstance) {
        configurableListableBeanFactory.registerSingleton(name, serviceClassInstance);
    }
}