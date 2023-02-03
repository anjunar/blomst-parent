package com.anjunar.blomst;

import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.security.User;
import com.google.common.base.Strings;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(filterName = "token", urlPatterns = "/service/*")
public class TokenServletFilter implements Filter {

    private final IdentityManager identityManager;

    private final EntityManager entityManager;

    @Inject
    public TokenServletFilter(IdentityManager identityManager, EntityManager entityManager) {
        this.identityManager = identityManager;
        this.entityManager = entityManager;
    }

    public TokenServletFilter() {
        this(null, null);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest httpServletRequest) {
            String queryString = httpServletRequest.getQueryString();
            if (! Strings.isNullOrEmpty(queryString)) {
                String[] queryParams = queryString.split("&");
                for (String queryParam : queryParams) {
                    String[] segments = queryParam.split("=");
                    String key = segments[0];
                    String value = segments[1];

                    if (key.equals("token")) {
                        try {
                            if (! authenticate(value)) {
                                if (response instanceof HttpServletResponse httpServletResponse) {
                                    httpServletResponse.setStatus(403);
                                }
                            }
                        } catch (NoResultException e) {
                            if (response instanceof HttpServletResponse httpServletResponse) {
                                httpServletResponse.setStatus(403);
                            }
                        }
                    }
                }
            }
        }
        chain.doFilter(request, response);
    }

    public boolean authenticate(String token) {
        if (! identityManager.isLoggedIn()) {
            User user = entityManager.createQuery("select u from User u where u.token = :token", User.class)
                    .setParameter("token", token)
                    .getSingleResult();
            return identityManager.authenticate(user);
        }
        return false;
    }

}
