package org.frank.rest4j.logic;

import org.frank.rest4j.annotation.RestAction;
import org.frank.rest4j.annotation.RestClient;
import org.frank.rest4j.annotation.RestParam;
import org.frank.rest4j.constant.Format;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by FrankJ on 9.7.2015.
 */
public class RestClientMethodsHandler implements InvocationHandler {

    private static final org.slf4j.Logger Logger = LoggerFactory.getLogger(RestClientMethodsHandler.class);

    private static final RestTemplate restTemplate = new RestTemplate();

    private String serverUrl;
    private Format format;

    private Map<String, RestMethod> methodsMap;

    public RestClientMethodsHandler(Class interfaceClass) {
        RestClient restClient = (RestClient) interfaceClass.getAnnotation(RestClient.class);

        serverUrl = restClient.url();
        format = restClient.format();

        methodsMap = createMethodMap(interfaceClass);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Logger.info("Method {} called from {} with {}.", method.getName(), proxy.getClass().getName(), args);

        RestMethod restMethod = methodsMap.get(method.getName());
        String actionLink = restMethod.getLink(serverUrl);

        ResponseEntity responseEntity = restTemplate.exchange(actionLink, restMethod.getMethod(), null, restMethod.getReturnType(), createParameterMap(method,args));

        return responseEntity.getBody();
    }

    /**
     * This method prepare parameter map (key value pairs - name of parameter and parameter value)
     * for later replacement. Regarding to the fact that is not possible to obtain method parameter name
     * during runtime without additional debug information passed by compiler, this method tries to do it this way
     * and if not success it tries to resolve it from annotation RestParam
     * 
     * @param method Method itself with annotation
     * @param args Real arguments from call
     * @return prepared named parameter map
     */
    private Map<String, Object> createParameterMap(Method method, Object[] args){
        Map<String, Object> parameterMap = new HashMap<String, Object>();

        TypeVariable<Method>[] methodParameters = method.getTypeParameters();

        if(methodParameters == null || methodParameters.length == 0){
            Annotation[][] parametersAnnotations = method.getParameterAnnotations();

            for (int i = 0; i < parametersAnnotations.length; i++) {
                Annotation[] parameterAnnotations = parametersAnnotations[i];

                if(parameterAnnotations.length > 0){
                    RestParam restParam = (RestParam) parameterAnnotations[0];

                    parameterMap.put(restParam.value(), args[i]);
                }
            }

        }else {
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
    private Map<String, RestMethod> createMethodMap(Class sourceInterface){
        Map<String, RestMethod> tempMethodMap = new HashMap<String, RestMethod>();

        Method[] clientMethods = sourceInterface.getMethods();
        for(Method method : clientMethods){
            RestAction restAction = method.getAnnotation(RestAction.class);

            if(restAction != null){
                RestMethod restMethod = new RestMethod();
                restMethod.setUrlFragment(restAction.urlFragment());
                restMethod.setMethod(restAction.method());
                restMethod.setReturnType(method.getReturnType().getName().equals("void") ? null : method.getReturnType());
                tempMethodMap.put(method.getName(), restMethod);
            }
        }

        return tempMethodMap;
    }
}
