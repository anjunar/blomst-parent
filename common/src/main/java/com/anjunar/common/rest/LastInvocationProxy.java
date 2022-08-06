package com.anjunar.common.rest;

import java.util.List;

public interface LastInvocationProxy {

    MethodInvocation getInvocation();

    List<MethodInvocation> getInvocations();

}
