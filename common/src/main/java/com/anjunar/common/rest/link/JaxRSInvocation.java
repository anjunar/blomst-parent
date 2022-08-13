package com.anjunar.common.rest.link;

import com.anjunar.common.rest.MethodPredicate;
import com.anjunar.common.rest.api.Link;
import com.anjunar.common.rest.api.LinkType;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.introspector.bean.BeanIntrospector;
import com.anjunar.introspector.bean.BeanModel;
import com.anjunar.introspector.bean.BeanProperty;
import com.anjunar.introspector.type.TypeResolver;
import com.anjunar.introspector.type.resolved.ResolvedAnnotatedElement;
import com.anjunar.introspector.type.resolved.ResolvedMethod;
import com.anjunar.introspector.type.resolved.ResolvedParameter;
import com.anjunar.introspector.type.resolved.ResolvedType;
import com.anjunar.jsr339.ResourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.ext.ParamConverter;
import jakarta.ws.rs.ext.ParamConverterProvider;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class JaxRSInvocation {

    private static final Logger log = LoggerFactory.getLogger(JaxRSInvocation.class);

    private final ParamConverterProvider converterProvider;
    private final UriBuilder uriBuilder;
    private final EntityManager entityManager;
    private final IdentityProvider identityProvider;

    private String rel;
    private String httpMethod;
    private Object body;
    private LinkType type;

    private final Object[] args;
    private final ResolvedMethod<?> method;

    private Map<String, Object> params = new HashMap<>();

    public JaxRSInvocation(ResolvedMethod<?> resolvedMethod,
                           Object[] arguments,
                           ParamConverterProvider converterProvider,
                           UriBuilder uriBuilder,
                           EntityManager entityManager,
                           IdentityProvider identityProvider) {
        this.converterProvider = converterProvider;
        this.uriBuilder = uriBuilder;
        this.method = resolvedMethod;
        this.args = arguments;

        rel = resolvedMethod.getName();
        this.entityManager = entityManager;
        this.identityProvider = identityProvider;

        final Path classPath = resolvedMethod.getEnclosingType().getAnnotation(Path.class);
        final Path methodPath = method.getAnnotation(Path.class);

        if (classPath != null && methodPath != null) {
            uriBuilder.path(classPath.value());
            uriBuilder.path(methodPath.value());
        }

        if (classPath == null && methodPath != null) {
            uriBuilder.path(methodPath.value());
        }

        if (classPath != null && methodPath == null) {
            uriBuilder.path(classPath.value());
        }

        httpMethod = ResourceUtil.httpMethod(resolvedMethod);

        for (int index = 0; index < arguments.length; index++) {
            Object arg = arguments[index];
            ResolvedParameter<?> parameter = resolvedMethod.getParameters().get(index);

            readParameter(arg, parameter);

            BeanParam beanParam = parameter.getAnnotation(BeanParam.class);
            Context contextParam = parameter.getAnnotation(Context.class);
            if (beanParam != null && arg != null) {

                BeanModel<?> beanModel = BeanIntrospector.create(parameter.getType());

                for (BeanProperty<?, ?> beanProperty : beanModel.getProperties()) {

                    readParameter(((BeanProperty<Object, ?>) beanProperty).apply(arg), beanProperty);
                }
            } else if (contextParam != null && arg != null){
                // No OP
            } else {
                body = arg;
            }

        }

/*
        if (httpMethod == null) {
            child = new URLBuilder<>(resolvedMethod.getReturnType().getRawType(), uriBuilder, identity, entityManager, this.converterProvider);
            return child.instance();
        }
*/
    }

    private void readParameter(Object arg, ResolvedAnnotatedElement parameter) {
        if (arg != null) {
            ParamConverter converter = converterProvider.getConverter(arg.getClass(), null, parameter.getAnnotations());
            if (converter != null) {
                arg = converter.toString(arg);
            }
        }

        PathParam pathParam = parameter.getAnnotation(PathParam.class);
        if (pathParam != null) {
            if (arg instanceof Collection) {
                params.put(pathParam.value(), ((Collection<?>) arg).toArray());
            } else {
                params.put(pathParam.value(), arg);
            }
        }

        QueryParam queryParam = parameter.getAnnotation(QueryParam.class);
        if (queryParam != null && arg != null) {
            if (arg instanceof Collection) {
                uriBuilder.queryParam(queryParam.value(), ((Collection<?>) arg).toArray());
            } else {
                uriBuilder.queryParam(queryParam.value(), arg);
            }
        }

        MatrixParam matrixParam = parameter.getAnnotation(MatrixParam.class);
        if (matrixParam != null && arg != null) {
            if (arg instanceof Collection) {
                uriBuilder.matrixParam(matrixParam.value(), ((Collection<?>) arg).toArray());
            } else {
                uriBuilder.matrixParam(matrixParam.value(), arg);
            }
        }
    }

    private String buildMethod() {
        if (httpMethod != null) {
            return httpMethod;
        }

//        return child.buildMethod();
        return null;
    }

    public boolean hasRoles(String[] roles) {
        for (String role : roles) {
            if (identityProvider.hasRole(role)) {
                return true;
            }
        }
        return false;
    }

    public void build(BiConsumer<String, Link> consumer) {

        URI url = uriBuilder.buildFromMap(params);

        RolesAllowed rolesAllowed = method.getAnnotation(RolesAllowed.class);

        LinkDescription linkDescription = method.getAnnotation(LinkDescription.class);

        if (rolesAllowed == null) {

            consumer.accept(rel, new Link("service" + url.toASCIIString(), buildMethod(), rel, linkDescription == null ? null : linkDescription.value()));

        } else {
            if (hasRoles(rolesAllowed.value())) {

                MethodPredicate methodPredicate = method.getAnnotation(MethodPredicate.class);

                if (methodPredicate == null) {
                    consumer.accept(rel, new Link("service" + url.toASCIIString(), buildMethod(), rel, linkDescription == null ? null : linkDescription.value()));
                } else {

                    Class<?> resolverCLass = methodPredicate.value();

                    try {
                        Constructor<?> constructor = resolverCLass.getDeclaredConstructor(IdentityProvider.class, EntityManager.class);
                        Object resolverInstance = constructor.newInstance(identityProvider, entityManager);

                        ResolvedType<?> resolvedType = TypeResolver.resolve(resolverCLass);
                        ResolvedMethod<Object> resolvedMethod = (ResolvedMethod<Object>) resolvedType.getMethods().get(0);

                        boolean result = (boolean) resolvedMethod.invoke(resolverInstance, args[0]);

                        if (result) {
                            consumer.accept(rel, new Link("service" + url.toASCIIString(), buildMethod(), rel, linkDescription == null ? null : linkDescription.value()));
                        }

                    } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
                        log.error(e.getMessage());
                    }

                }

            }
        }

    }


    public void setRel(String value) {
        this.rel = value;
    }
}
