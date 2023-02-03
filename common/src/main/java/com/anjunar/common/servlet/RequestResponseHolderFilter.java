package com.anjunar.common.servlet;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;

/**
 * This filter stores the current request and response in the {@link RequestResponseHolder}.
 */
@WebFilter(filterName = "responseHolder", urlPatterns = "*")
public class RequestResponseHolderFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException
    {
        // nothing yet
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException
    {

        /*
         * Typically the request is bound to the thread in RequestResponseHolderListener. But as this listener was added
         * after the initial release of the Servlet module, the filter will also do it if necessary.
         */
        final boolean alreadyBound = RequestResponseHolder.REQUEST.isBound();
        if (!alreadyBound)
        {
            RequestResponseHolder.REQUEST.bind(request);
        }

        try
        {

            RequestResponseHolder.RESPONSE.bind(response);
            try
            {
                chain.doFilter(request, response);
            }
            finally
            {
                RequestResponseHolder.RESPONSE.release();
            }

        }
        finally
        {
            if (!alreadyBound)
            {
                RequestResponseHolder.REQUEST.release();
            }
        }

    }

    @Override
    public void destroy()
    {
        // nothing yet
    }

}