package org.frank.rest4j.provider;

import org.frank.rest4j.RestClient;
import org.frank.rest4j.proxy.RestDynamicProxyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;

import javax.annotation.PostConstruct;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.Set;


/**
 * Created by Jiøí on 1. 7. 2015.
 */

public class RestInterfacesScanner {

    private final Logger Logger = LoggerFactory.getLogger(RestInterfacesScanner.class);

    @Autowired
    private SpringContextManipulator springContextManipulator;

    private String basePackage;

    public RestInterfacesScanner(String basePackage) {
        this.basePackage = basePackage;
    }

    public SpringContextManipulator getSpringContextManipulator() {
        return springContextManipulator;
    }

    public void setSpringContextManipulator(SpringContextManipulator springContextManipulator) {
        this.springContextManipulator = springContextManipulator;
    }

    @PostConstruct
    public void init(){
        Logger.debug("Started initializing of scanner bean.");

        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningRestInterfaceProvider();
        Set<BeanDefinition> beanDefinitions = provider.findCandidateComponents(basePackage);

        for (BeanDefinition beanDefinition : beanDefinitions) {
            Logger.debug("Resolved interface " + beanDefinition.getBeanClassName());

            RestClient restClient = null;

            try {
                restClient = initializeServiceProxy(beanDefinition);
            } catch (ClassNotFoundException e) {
                Logger.error("Interface couldn´t be dynamicaly proxied due to error.", e);
                continue;
            }

            springContextManipulator.addServiceToContext(beanDefinition.getBeanClassName(), restClient);
        }
    }

    private RestClient initializeServiceProxy(BeanDefinition beanDefinition) throws ClassNotFoundException {
        InvocationHandler handler = new RestDynamicProxyHandler();

        RestClient proxy = (RestClient) Proxy.newProxyInstance(
                RestClient.class.getClassLoader(),
                new Class[]{Class.forName(beanDefinition.getBeanClassName()), RestClient.class},
                handler);

        return proxy;

    }
}
