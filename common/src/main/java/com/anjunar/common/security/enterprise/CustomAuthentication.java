package com.anjunar.common.security.enterprise;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.AutoApplySession;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.authentication.mechanism.http.RememberMe;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStoreHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@AutoApplySession
@ApplicationScoped
@RememberMe(cookieMaxAgeSeconds = Integer.MAX_VALUE)
public class CustomAuthentication implements HttpAuthenticationMechanism {

    private final IdentityStoreHandler identityStoreHandler;

    @Inject
    public CustomAuthentication(IdentityStoreHandler identityStoreHandler) {
        this.identityStoreHandler = identityStoreHandler;
    }

    public CustomAuthentication() {
        this(null);
    }

    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) throws AuthenticationException {

        Credential credential = (Credential) request.getAttribute("credential");

        if (credential != null) {

            CredentialValidationResult result = identityStoreHandler.validate(credential);

            return httpMessageContext.notifyContainerAboutLogin(result);

        }

        if (httpMessageContext.isProtected()) {
            return httpMessageContext.responseUnauthorized();
        }

        return httpMessageContext.doNothing();
    }

    @Override
    public void cleanSubject(HttpServletRequest request, HttpServletResponse response, HttpMessageContext httpMessageContext) {
        httpMessageContext.cleanClientSubject();
    }
}
