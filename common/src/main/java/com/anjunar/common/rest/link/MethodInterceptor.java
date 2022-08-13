package com.anjunar.common.rest.link;

import com.anjunar.introspector.type.TypeResolver;
import com.anjunar.introspector.type.resolved.ResolvedMethod;
import com.anjunar.introspector.type.resolved.ResolvedType;
import javassist.util.proxy.MethodHandler;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MethodInterceptor implements MethodHandler, LastInvocationProxy {

    private final MethodInterceptor last;

    private MethodInvocation invocation;

    public MethodInterceptor(MethodInterceptor last) {
        this.last = last;
    }

    @Override
    public MethodInvocation getInvocation() {
        return invocation;
    }

    @Override
    public List<MethodInvocation> getInvocations() {
        List<MethodInvocation> invocations = new ArrayList<>();
        if (last != null) {
            List<MethodInvocation> result = last.getInvocations();
            invocations.addAll(result);
        }
        if (invocation != null) {
            invocations.add(invocation);
        }
        return invocations;
    }

    @Override
    public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {
        ResolvedType<?> type = TypeResolver.resolve(self.getClass());

        final ResolvedMethod<?> resolvedMethod = type.getMethods()
                .stream()
                .filter(method -> method.equalSignature(thisMethod))
                .findFirst()
                .get();

        invocation = new MethodInvocation(resolvedMethod, args);

        return WebURLBuilderFactory.createProxy(thisMethod.getReturnType(), new MethodInterceptor(this));
    }
}
