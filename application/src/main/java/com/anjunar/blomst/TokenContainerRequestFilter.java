package com.anjunar.blomst;

import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.security.User;
import com.google.common.base.Strings;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.ext.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@Provider
public class TokenContainerRequestFilter implements ContainerRequestFilter {

    private final static Logger log = LoggerFactory.getLogger(TokenContainerRequestFilter.class);

    private final EntityManager entityManager;

    private final IdentityManager identityManager;

    @Inject
    public TokenContainerRequestFilter(EntityManager entityManager, IdentityManager identityManager) {
        this.entityManager = entityManager;
        this.identityManager = identityManager;
    }

    public TokenContainerRequestFilter() {
        this(null, null);
    }

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {

        log.info("MediaType : " + containerRequestContext.getMediaType());

        String token = containerRequestContext.getHeaderString("token");
        if (! Strings.isNullOrEmpty(token)) {
            token(token);
        }

        String jhToken = containerRequestContext.getHeaderString("JH-JenniJenniAuthToken");
        if (! Strings.isNullOrEmpty(jhToken)) {
            token(jhToken);
        }

    }

    public void token(String token) {
        if (identityManager.isLoggedIn()) {
            User user = entityManager.createQuery("select u from User u where u.token = :token", User.class)
                    .setParameter("token", token)
                    .getSingleResult();

            identityManager.authenticate(user);
        }
    }

}
