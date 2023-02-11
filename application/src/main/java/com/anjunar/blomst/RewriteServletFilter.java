package com.anjunar.blomst;

import com.google.common.collect.Lists;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebFilter(filterName = "rewriteFilter", urlPatterns = "*")
public class RewriteServletFilter implements Filter {

    private static final List<String> blacklist = Lists.newArrayList("/service");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);

        URL resource = null;
        try {
            resource = filterConfig.getServletContext().getResource("/");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        File directory = new File(resource.getFile());
        String[] list = directory.list();
        for (String fileName : list) {
            blacklist.add("/" + fileName);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        if (request instanceof HttpServletRequest httpServletRequest) {
            String requestURI = httpServletRequest.getRequestURI();

            if (blacklist.stream().noneMatch(requestURI::startsWith)) {
                request.getServletContext().getRequestDispatcher("/index.html").forward(request, response);
            }

        }

        chain.doFilter(request, response);
    }
}
