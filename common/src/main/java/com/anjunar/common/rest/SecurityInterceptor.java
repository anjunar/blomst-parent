package com.anjunar.common.rest;

import com.anjunar.common.security.IdentityProvider;
import de.anjunar.introspector.type.TypeResolver;
import de.anjunar.introspector.type.resolved.ResolvedMethod;
import de.anjunar.introspector.type.resolved.ResolvedType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.ws.rs.NotAuthorizedException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

@MethodPredicate
@Interceptor
public class SecurityInterceptor {

    private static final Logger log = LoggerFactory.getLogger(SecurityInterceptor.class);

    private final IdentityProvider identityProvider;

    @Inject
    public SecurityInterceptor(IdentityProvider identityProvider) {
        this.identityProvider = identityProvider;
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
                Constructor<?> constructor = resolverCLass.getDeclaredConstructor(IdentityProvider.class);
                Object resolverInstance = constructor.newInstance(identityProvider);

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
