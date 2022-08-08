package com.anjunar.common.security;

import com.anjunar.common.security.enterprise.Authenticator;
import com.anjunar.common.security.enterprise.CivilCredential;
import com.anjunar.common.i18n.i18nResolver;
import com.anjunar.common.rest.LinkDescription;
import com.anjunar.common.rest.api.Link;
import com.anjunar.common.rest.api.LinkType;
import com.anjunar.jsr339.Operation;
import com.anjunar.jsr339.Resource;
import com.anjunar.jsr339.cdi.JaxRSExtension;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.credential.Password;
import jakarta.transaction.Transactional;
import java.io.Serializable;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

@SessionScoped
public class IdentityProvider implements Serializable {

    private final IdentityService service;

    private final JaxRSExtension extension;

    private final Authenticator authenticator;

    private final i18nResolver resolver;

    @Inject
    public IdentityProvider(IdentityService service, JaxRSExtension extension, Authenticator authenticator, i18nResolver resolver) {
        this.service = service;
        this.extension = extension;
        this.authenticator = authenticator;
        this.resolver = resolver;
    }

    public IdentityProvider() {
        this(null, null, null, null);
    }

    public boolean authenticate(User user) {
        CivilCredential credential = new CivilCredential(user.getFirstName(), user.getLastName(), user.getBirthDate(), new Password(user.getPassword()));

        AuthenticationStatus authenticate = authenticator.authenticate(credential);

        return authenticate == AuthenticationStatus.SUCCESS;
    }

    public void logout() {
        authenticator.logout();
    }

    public boolean isLoggedIn() {
        return authenticator.getUserPrincipal() != null;
    }

    public User getUser() {
        Principal principal = authenticator.getUserPrincipal();
        if (principal == null) {
            return null;
        }
        return findUser(UUID.fromString(principal.getName()));
    }

    public User findUser(UUID id) {
        return service.findUser(id);
    }

    public User findUser(String firstName, String lastName, LocalDate birthDate) {
        return service.findUser(firstName, lastName, birthDate);
    }

    public User findUser(String email) {
        return service.findUser(email);
    }

    @Transactional
    public boolean hasRole(String role) {
        User user = service.findUser(getUser().getId());
        Set<Role> roles = user.getRoles();
        for (Role group : roles) {
            if (group.getName().equals(role)) {
                return true;
            }
        }
        return false;
    }

    @Transactional
    public boolean hasPermission(String value, String method) {
        if (isLoggedIn()) {
            User user = service.findUser(getUser().getId());
            Set<Role> relationships = user.getRoles();
            for (Role role : relationships) {
                Set<Permission> permissions = role.getPermissions();
                for (Permission permission : permissions) {
                    if (permission.getUrl().contains(value) && permission.getMethod().equals(method)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Transactional
    public void createLink(String url, String method, String rel, LinkType type, Consumer<Link> loader) {
        String[] rawUrl = url.split("\\?");

        Set<Resource<?>> resources = extension.getResources();
        for (Resource<?> resource : resources) {
            for (Operation operation : resource.getOperations()) {
                if (operation.getUrl().equals(rawUrl[0]) && operation.getHttpMethod().equals(method)) {
                    RolesAllowed rolesAllowed = operation.getMethod().getAnnotation(RolesAllowed.class);
                    LinkDescription linkDescription = operation.getMethod().getAnnotation(LinkDescription.class);
                    if (rolesAllowed != null) {
                        for (String role : rolesAllowed.value()) {
                            if (hasRole(role)) {
                                Link link = new Link("service/" + url, method, rel, linkDescription == null ? null : linkDescription.value());
                                loader.accept(link);
                                break;
                            }
                        }
                    } else {
                        Link link = new Link("service/" + url, method, rel, linkDescription == null ? null : linkDescription.value());
                        loader.accept(link);
                    }
                }
            }
        }
    }

    public Locale getLanguage() {
        return resolver.getLocale();
    }

    public void setLanguage(Locale language) {
        resolver.setLocale(language);
    }

    public User findUserByToken(String token) {
        return service.findUserByToken(token);
    }
}
