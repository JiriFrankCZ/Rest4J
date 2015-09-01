package org.frank.rest4j.repository.config.xml;

import org.frank.rest4j.constant.Constants;
import org.frank.rest4j.repository.ComponentsScanner;
import org.frank.rest4j.repository.config.BeanDefinitionFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.Assert;
import org.w3c.dom.Element;

import java.util.Set;


/**
 * Created by Jiøí on 7. 7. 2015.
 */
public class RestRepositoryNamespaceHandler extends NamespaceHandlerSupport {
	private final org.slf4j.Logger Logger = LoggerFactory.getLogger(RestRepositoryNamespaceHandler.class);

	protected static final String BASE_PACKAGE_ATTRIBUTE_NAME = "basePackage";

	private ComponentsScanner componentsScanner;
	private BeanDefinitionFactory beanDefinitionFactory;

	public void init() {
		componentsScanner = new ComponentsScanner(Constants.DEFAULT_ANNOTATION_FILTER);
		beanDefinitionFactory = new BeanDefinitionFactory(Constants.FACTORY_CLASS, Constants.FACTORY_METHOD_NAME);
		registerBeanDefinitionParser("repositories", new RestRepositoryBeanDefinitionParser());
	}

	private class RestRepositoryBeanDefinitionParser implements BeanDefinitionParser {

		public BeanDefinition parse(Element element, ParserContext parserContext) {

			String basePackage = element.getAttribute(BASE_PACKAGE_ATTRIBUTE_NAME);
			Assert.notNull(basePackage, "Base package has to be defined in every case.");
			Logger.info("Base package was set to {} and will be scaned for RestRespository source.", basePackage);

			Set<String> interfaces = componentsScanner.scan(basePackage);

			beanDefinitionFactory.registerInterfaces(parserContext.getRegistry(), interfaces);

			return null;
		}

	}
}
