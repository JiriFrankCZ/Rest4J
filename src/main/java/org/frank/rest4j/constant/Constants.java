package org.frank.rest4j.constant;

import org.frank.rest4j.annotation.Client;
import org.frank.rest4j.construction.RestInterfaceProxyFactory;

/**
 * Created by FrankJ on 31.7.2015.
 */
public class Constants {
	// Default annotation for identifiing Rest4J interfaces
	public static final Class DEFAULT_ANNOTATION_FILTER = Client.class;
	// Default factory for creating dynamic proxies
	public static final Class FACTORY_CLASS = RestInterfaceProxyFactory.class;
	// Method in factory for create new instance
	public static final String FACTORY_METHOD_NAME = "createInstance";
}
