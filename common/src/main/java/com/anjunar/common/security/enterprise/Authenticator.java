package com.anjunar.common.security.enterprise;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.SecurityContext;
import jakarta.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import jakarta.security.enterprise.credential.Credential;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.security.Principal;

@RequestScoped
public class Authenticator {

    private final SecurityContext securityContext;

    private final HttpServletRequest request;

    private final HttpServletResponse response;

    @Inject
    public Authenticator(SecurityContext securityContext, HttpServletRequest request,  HttpServletResponse response) {
        this.securityContext = securityContext;
        this.request = request;
        this.response = response;
    }

    public Authenticator() {
        this(null, null, null);
    }

    public AuthenticationStatus authenticate(Credential credential) {
        AuthenticationParameters parameters = new AuthenticationParameters();

        parameters.setCredential(credential);
        parameters.setNewAuthentication(true);
        parameters.setRememberMe(true);

        request.setAttribute("credential", credential);

        return securityContext.authenticate(request, response, parameters);
    }

    public void logout() {
        try {
            request.logout();
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    public Principal getUserPrincipal() {
        return request.getUserPrincipal();
    }
}
