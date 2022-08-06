package com.anjunar.common.rest;

import com.anjunar.common.security.IdentityProvider;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.spi.CDI;
import javax.persistence.EntityManager;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.ext.ParamConverterProvider;
import java.util.ArrayList;
import java.util.List;

public class WebURLBuilderFactory {

    private static final Logger log = LoggerFactory.getLogger(WebURLBuilderFactory.class);

    private static ParamConverterProvider paramConverterProvider() {
        return CDI.current().select(ParamConverterProvider.class).get();
    }

    private static EntityManager entityManager() {
        return CDI.current().select(EntityManager.class).get();
    }

    private static IdentityProvider identity() {
        return CDI.current().select(IdentityProvider.class).get();
    }

    public static WebURLBuilder linkTo(Object invocation) {
        Factory getCallbacks = (Factory) invocation;
        Callback[] interceptor = getCallbacks.getCallbacks();
        List<MethodInvocation> invocations = ((MethodInterceptor)interceptor[0]).getInvocations();
        List<JaxRSInvocation> jaxRSInvocations = new ArrayList<>();
        UriBuilder uriBuilder = UriBuilder.fromPath("/");

        for (MethodInvocation methodInvocation : invocations) {
            JaxRSInvocation jaxRSInvocation = new JaxRSInvocation(
                    methodInvocation.getMethod(),
                    methodInvocation.getArguments(),
                    paramConverterProvider(),
                    uriBuilder,
                    entityManager(),
                    identity()
            );
            jaxRSInvocations.add(jaxRSInvocation);
        }

        return new WebURLBuilder(jaxRSInvocations.get(jaxRSInvocations.size() -1));
    }

    public static <E> E methodOn(Class<E> aClass) {
        return createProxy(aClass, new MethodInterceptor(null));
    }

    public static <B> B createProxy(Class<B> aClass, MethodInterceptor methodHandler) {
        Enhancer enhancer = new Enhancer();
        if (aClass.isInterface()) {
            enhancer.setInterfaces(new Class[]{aClass});
        } else {
            enhancer.setSuperclass(aClass);
        }
        enhancer.setCallback(methodHandler);
        return (B) enhancer.create();
    }


}
