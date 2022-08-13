package com.anjunar.common.rest.link;

import com.anjunar.common.rest.api.Link;

import java.util.function.BiConsumer;

public class WebURLBuilder {

    private final JaxRSInvocation jaxRSInvocation;

    public WebURLBuilder(JaxRSInvocation jaxRSInvocation) {
        this.jaxRSInvocation = jaxRSInvocation;
    }

    public WebURLBuilder withRel(String value) {
        jaxRSInvocation.setRel(value);
        return this;
    }

    public void build(BiConsumer<String, Link> consumer) {
        jaxRSInvocation.build(consumer);
    }

}
