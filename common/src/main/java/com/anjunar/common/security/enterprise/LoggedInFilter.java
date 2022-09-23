package com.anjunar.common.security.enterprise;

import com.anjunar.common.security.IdentityStore;
import com.anjunar.common.security.User;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.security.Principal;
import java.util.Objects;
import java.util.UUID;

@WebFilter("/service/*")
public class LoggedInFilter implements Filter {

    private final IdentityStore identityStore;

    private final EntityManager entityManager;

    @Inject
    public LoggedInFilter(IdentityStore identityStore, EntityManager entityManager) {
        this.identityStore = identityStore;
        this.entityManager = entityManager;
    }

    public LoggedInFilter() {
        this(null, null);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        Principal principal = httpServletRequest.getUserPrincipal();

        if (Objects.nonNull(principal)) {
            User user = entityManager.find(User.class, UUID.fromString(principal.getName()));

            identityStore.setUser(user);
        }

        chain.doFilter(request, response);
    }

}
