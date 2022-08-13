package com.anjunar.common.rest.link;

import com.anjunar.common.security.IdentityProvider;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.Proxy;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.enterprise.inject.spi.CDI;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.ext.ParamConverterProvider;
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
        ProxyObject getCallbacks = (ProxyObject) invocation;
        MethodHandler interceptor = getCallbacks.getHandler();
        List<MethodInvocation> invocations = ((MethodInterceptor)interceptor).getInvocations();
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

    public static <B> B createProxy(Class<B> aClass, MethodHandler methodHandler) {
        ProxyFactory proxyFactory = new ProxyFactory();
        if (aClass.isInterface()) {
            proxyFactory.setInterfaces(new Class[]{aClass});
        } else {
            proxyFactory.setSuperclass(aClass);
        }
        Class<?> factoryClass = proxyFactory.createClass();
        try {
            B instance = (B) factoryClass.newInstance();
            ((Proxy) instance).setHandler(methodHandler);
            return instance;
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


}
