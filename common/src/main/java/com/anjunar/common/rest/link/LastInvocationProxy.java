package com.anjunar.common.rest.link;

import java.util.List;

public interface LastInvocationProxy {

    MethodInvocation getInvocation();

    List<MethodInvocation> getInvocations();

}
