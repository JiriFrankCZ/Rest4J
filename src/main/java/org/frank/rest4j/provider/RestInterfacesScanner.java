package org.frank.rest4j.provider;


import org.frank.rest4j.general.RestClientProxy;
import org.frank.rest4j.proxy.RestDynamicProxyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.PriorityOrdered;
import org.springframework.core.type.AnnotationMetadata;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by Ji�� on 1. 7. 2015.
 */

public class RestInterfacesScanner implements ImportBeanDefinitionRegistrar {

    private final Logger Logger = LoggerFactory.getLogger(RestInterfacesScanner.class);


    private String basePackage;

    public RestInterfacesScanner(String basePackage) {
        this.basePackage = basePackage;
    }

    private RestClientProxy initializeServiceProxy(BeanDefinition beanDefinition) throws ClassNotFoundException {
        InvocationHandler handler = new RestDynamicProxyHandler();

        RestClientProxy proxy = (RestClientProxy) Proxy.newProxyInstance(
                RestClientProxy.class.getClassLoader(),
                new Class[]{Class.forName(beanDefinition.getBeanClassName()), RestClientProxy.class},
                handler);

        return proxy;

    }

    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition("asdasd","asd");
    }

    private Set<Class> scanInterfaces(String basePackage){
        Logger.debug("Started initializing of scanner bean.");

        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningRestInterfaceProvider();
        Set<BeanDefinition> beanDefinitions = provider.findCandidateComponents(basePackage);

        Set<Class> resolvedInterfaceList = new HashSet<Class>(beanDefinitions.size());

        for (BeanDefinition beanDefinition : beanDefinitions) {
            Logger.debug("Resolved interface " + beanDefinition.getBeanClassName());

            try {
                String className = beanDefinition.getBeanClassName();
                resolvedInterfaceList.add(Class.forName(className));
            } catch (ClassNotFoundException e) {
                Logger.error("Problems occured during resolving Class object for class name.", e);
            }
        }

        return resolvedInterfaceList;
    }
}
