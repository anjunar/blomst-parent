package com.anjunar.common.rest.link;

import com.anjunar.introspector.type.resolved.ResolvedMethod;

public class MethodInvocation {

    private final ResolvedMethod<?> method;

    private final Object[] arguments;

    public MethodInvocation(ResolvedMethod<?> method, Object[] arguments) {
        this.method = method;
        this.arguments = arguments;
    }

    public ResolvedMethod<?> getMethod() {
        return method;
    }

    public Object[] getArguments() {
        return arguments;
    }
}
