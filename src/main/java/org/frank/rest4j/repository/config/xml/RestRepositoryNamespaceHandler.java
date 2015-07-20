package org.frank.rest4j.repository.config.xml;

import org.frank.rest4j.annotation.RestClient;
import org.frank.rest4j.construction.RestInterfaceProxyFactory;
import org.frank.rest4j.repository.ComponentsScanner;
import org.frank.rest4j.repository.GenericMethodInvokingFactoryBean;
import org.frank.rest4j.util.CommonsUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.Assert;
import org.w3c.dom.Element;

import java.util.Arrays;
import java.util.Set;


/**
 * Created by Jiøí on 7. 7. 2015.
 */
public class RestRepositoryNamespaceHandler extends NamespaceHandlerSupport {

    private final org.slf4j.Logger Logger = LoggerFactory.getLogger(RestRepositoryNamespaceHandler.class);

    private static final Class DEFAULT_ANNOTATION_FILTER = RestClient.class;

    private static final String BASE_PACKAGE_ATTRIBUTE_NAME = "basePackage";
    private static final Class FACTORY_CLASS = RestInterfaceProxyFactory.class;
    private static final String FACTORY_METHOD_NAME = "createInstance";


    private ComponentsScanner componentsScanner;

    public void init() {
        componentsScanner = new ComponentsScanner(DEFAULT_ANNOTATION_FILTER);
        registerBeanDefinitionParser("repositories", new RestRepositoryBeanDefinitionParser());
    }

    private class RestRepositoryBeanDefinitionParser implements BeanDefinitionParser {

        public BeanDefinition parse(Element element, ParserContext parserContext) {

            String basePackage = element.getAttribute(BASE_PACKAGE_ATTRIBUTE_NAME);
            Assert.notNull(basePackage, "Base package has to be defined in every case.");
            Logger.info("Base package was set to {} and will be scaned for RestRespository interfaces.", basePackage);

            Set<String> interfaces = componentsScanner.scan(basePackage);

            if(interfaces != null && interfaces.size() > 0){
                BeanDefinitionRegistry beanDefinitionRegistry = parserContext.getRegistry();

                for(String interfaceName : interfaces){
                    BeanDefinition beanDefinition = createBeanDefinition(interfaceName);

                    if(beanDefinition != null){
                        beanDefinitionRegistry.registerBeanDefinition(
                                CommonsUtil.getClassNameFromFQN(interfaceName),
                                beanDefinition
                        );
                    }
                }
            }
            return null;
        }
    }

    private BeanDefinition createBeanDefinition(String interfaceName){

        BeanDefinition beanDefinition = null;
        try {
            Class interfaceClass = Class.forName(interfaceName);

            beanDefinition = BeanDefinitionBuilder
                .rootBeanDefinition(GenericMethodInvokingFactoryBean.class)
                    .addPropertyValue("targetClass", FACTORY_CLASS)
                    .addPropertyValue("targetMethod", FACTORY_METHOD_NAME)
                    .addPropertyValue("targetType", interfaceClass)
                    .addPropertyValue("arguments", interfaceClass)
                    .setLazyInit(false)
                    .getBeanDefinition();

        } catch (ClassNotFoundException e) {
            Logger.error("Interface couldn´t be resolved for {}.",interfaceName);
        }

        return beanDefinition;
    }
}
