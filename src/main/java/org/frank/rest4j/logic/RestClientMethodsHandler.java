package org.frank.rest4j.logic;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.frank.rest4j.annotation.Action;
import org.frank.rest4j.annotation.Client;
import org.frank.rest4j.annotation.Param;
import org.frank.rest4j.constant.Format;
import org.frank.rest4j.exception.MethodCallException;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Entry point for handling call of proxy methods
 */
public class RestClientMethodsHandler implements InvocationHandler {

	private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(RestClientMethodsHandler.class);

	private ExecutorService executor = Executors.newFixedThreadPool(10);

	private final RestTemplate restTemplate = new RestTemplate();

	private String serverUrl;
	private Format format;

	private Map<String, RestMethod> methodsMap;

	public RestClientMethodsHandler(Class interfaceClass) {
		// Init values from annotation
		Client client = (Client) interfaceClass.getAnnotation(Client.class);

		serverUrl = client.value();
		format = client.format();

		// create HashMap of method for faster processing
		methodsMap = createMethodMap(interfaceClass);


		switch (client.authentificationType()){
			case BASIC:
				Logger.info("Basic authentification for " + serverUrl);

			case NONE:
			default:
				Logger.info("No authentification for " + serverUrl);
		}


//		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
//
//		//httpClient.get
//
//		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
//		restTemplate.setRequestFactory(requestFactory);
//
//		restTemplate.set
//		// set authentification if neede
//		HttpComponentsClientHttpRequestFactory requestFactory = (SimpleClientHttpRequestFactory) restTemplate.getRequestFactory();
//		DefaultHttpClient httpClient =				(DefaultHttpClient) requestFactory.getHttpClient();
//		httpClient.getCredentialsProvider().setCredentials(
//				new AuthScope(host, port, AuthScope.ANY_REALM),
//				new UsernamePasswordCredentials("name", "pass"));
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Exception {
		Logger.info("Method {} called with {} parametres.", method.getName(), args);

		RestMethod restMethod = methodsMap.get(method.getName());
		String actionLink = restMethod.getLink(serverUrl);

		Logger.debug("HTTP/{} {} async [{}] {}", restMethod.getMethod(), actionLink, restMethod.isAsync() , args);

		if(restMethod.isAsync()){
			return invokeSync(method, restMethod, actionLink, args);
		}else{
			return null;
		}
	}

	private Object invokeSync(Method method, RestMethod restMethod, String actionLink, Object[] args) {
		try {
			ResponseEntity responseEntity = restTemplate.exchange(actionLink, restMethod.getMethod(), null, restMethod.getReturnType(), createParameterMap(method, args));

			return responseEntity.getBody();

		}catch (HttpClientErrorException e){
			if (e.getStatusCode().equals(restMethod.getSuccessStatus())) {
				return e.getResponseBodyAsString();
			}else{
				Logger.error("Error occured during method call. Status returned {}.", e.getStatusCode());

				throw new MethodCallException(
						restMethod.getSuccessStatus(),
						e.getStatusCode(),
						e.getResponseHeaders(),
						e.getResponseBodyAsString()
				);
			}
		}
	}

	@Async
	public Future<Object> invokeAsync(Method method, RestMethod restMethod, String actionLink, Object[] args) {
		try {
			ResponseEntity responseEntity = restTemplate.exchange(actionLink, restMethod.getMethod(), null, restMethod.getReturnType(), createParameterMap(method, args));
			return new AsyncResult<Object>(responseEntity.getBody());

		}catch (HttpClientErrorException e){
			if (e.getStatusCode().equals(restMethod.getSuccessStatus())) {
				return new AsyncResult<Object>(e.getResponseBodyAsString());
			}else{
				Logger.error("Error occured during method call. Status returned {}.", e.getStatusCode());

				throw new MethodCallException(
						restMethod.getSuccessStatus(),
						e.getStatusCode(),
						e.getResponseHeaders(),
						e.getResponseBodyAsString()
				);
			}
		}
	}


	/**
	 * This method prepare parameter map (key value pairs - name of parameter and parameter value)
	 * for later replacement. Regarding to the fact that is not possible to obtain method parameter name
	 * during runtime without additional debug information passed by compiler, this method tries to do it this way
	 * and if not success it tries to resolve it from annotation Param
	 *
	 * @param method Method itself with annotation
	 * @param args   Real arguments from call
	 * @return prepared named parameter map
	 */
	private Map<String, Object> createParameterMap(Method method, Object[] args) {
		Map<String, Object> parameterMap = new HashMap<String, Object>();

		TypeVariable<Method>[] methodParameters = method.getTypeParameters();

		if (methodParameters == null || methodParameters.length == 0) {
			Annotation[][] parametersAnnotations = method.getParameterAnnotations();

			for (int i = 0; i < parametersAnnotations.length; i++) {
				Annotation[] parameterAnnotations = parametersAnnotations[i];

				if (parameterAnnotations.length > 0) {
					Param param = (Param) parameterAnnotations[0];

					parameterMap.put(param.value(), args[i]);
				}
			}

		} else {
			for (int i = 0; i < methodParameters.length; i++) {
				parameterMap.put(methodParameters[i].getName(), args[i]);
			}
		}

		return parameterMap;
	}

	/**
	 * Scans provided interface for rest actions and generate map of them for
	 * faster access during invocation.
	 *
	 * @param sourceInterface
	 * @return Map of methods
	 */
	private Map<String, RestMethod> createMethodMap(Class sourceInterface) {
		Map<String, RestMethod> tempMethodMap = new HashMap<String, RestMethod>();

		Method[] clientMethods = sourceInterface.getMethods();
		for (Method method : clientMethods) {
			Action action = method.getAnnotation(Action.class);
			HttpMethod httpMethod = action.method();

			if(method.isAnnotationPresent(org.frank.rest4j.annotation.Method.class)){
				httpMethod = method.getAnnotation(org.frank.rest4j.annotation.Method.class).value();
			}

			if (action != null) {
				RestMethod restMethod = new RestMethod();
				restMethod.setUrlFragment(action.value());
				restMethod.setMethod(httpMethod);
				restMethod.setSuccessStatus(action.status());
				restMethod.setReturnType(method.getReturnType().getName().equals("void") ? null : method.getReturnType());
				restMethod.setAsync(action.async());
				tempMethodMap.put(method.getName(), restMethod);
			}
		}

		return tempMethodMap;
	}
}
