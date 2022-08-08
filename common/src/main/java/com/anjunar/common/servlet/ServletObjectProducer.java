package com.anjunar.common.servlet;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.Typed;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;

public class ServletObjectProducer {

    @Produces
    @Typed(HttpServletResponse.class)
    @RequestScoped
    public HttpServletResponse getHttpServletResponse()
    {
        ServletResponse response = RequestResponseHolder.RESPONSE.get();
        if (response instanceof HttpServletResponse)
        {
            return (HttpServletResponse) response;
        }
        throw new IllegalStateException("The current response is not a HttpServletResponse");
    }
}
