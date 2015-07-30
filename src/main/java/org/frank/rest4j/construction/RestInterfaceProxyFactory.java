package org.frank.rest4j.construction;

import org.frank.rest4j.annotation.Client;
import org.frank.rest4j.logic.RestClientMethodsHandler;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;

/**
 * Created by FrankJ on 8.7.2015.
 */
public class RestInterfaceProxyFactory {

	private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(RestInterfaceProxyFactory.class);

	public static <T> T createInstance(Class<T> interfaceClass) throws ClassNotFoundException {
		Logger.info("Started creating proxy for interface {}.", interfaceClass.getCanonicalName());

		T restClient = (T) Proxy.newProxyInstance(
				RestInterfaceProxyFactory.class.getClassLoader(),
				new Class[]{interfaceClass, Client.class,},
				new RestClientMethodsHandler(interfaceClass)
		);

		return restClient;
	}
}
