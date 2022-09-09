package com.anjunar.common.rest;

import com.anjunar.common.security.IdentityManager;
import com.anjunar.introspector.type.TypeResolver;
import com.anjunar.introspector.type.resolved.ResolvedMethod;
import com.anjunar.introspector.type.resolved.ResolvedType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.ws.rs.NotAuthorizedException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@MethodPredicate
@Interceptor
public class SecurityInterceptor {

    private static final Logger log = LoggerFactory.getLogger(SecurityInterceptor.class);

    private final IdentityManager identityManager;

    @Inject
    public SecurityInterceptor(IdentityManager identityManager) {
        this.identityManager = identityManager;
    }

    public SecurityInterceptor() {
        this(null);
    }

    @AroundInvoke
    public Object manageTransaction(InvocationContext ctx) throws Exception {

        MethodPredicate methodPredicate = ctx.getMethod().getAnnotation(MethodPredicate.class);

        if (methodPredicate == null) {
            return ctx.proceed();
        } else {

            Class<?> resolverCLass = methodPredicate.value();

            try {
                Constructor<?> constructor = resolverCLass.getDeclaredConstructor(IdentityManager.class);
                Object resolverInstance = constructor.newInstance(identityManager);

                ResolvedType<?> resolvedType = TypeResolver.resolve(resolverCLass);
                ResolvedMethod<Object> resolvedMethod = (ResolvedMethod<Object>) resolvedType.getMethods().get(0);

                Object[] parameters = ctx.getParameters();

                boolean result = (boolean) resolvedMethod.invoke(resolverInstance, parameters[0]);

                if (result) {
                    return ctx.proceed();
                } else {
                    throw new NotAuthorizedException("Not Allowed");
                }


            } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                throw new Exception(e);
            }

        }
    }
}
