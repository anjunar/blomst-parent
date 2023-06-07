package com.anjunar.common.security;

import com.anjunar.common.i18n.Language;
import com.anjunar.common.security.enterprise.Authenticator;
import com.anjunar.common.i18n.i18nResolver;

import com.anjunar.common.security.enterprise.EmailCredential;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.credential.Password;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.transaction.Transactional;
import java.io.Serializable;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@ApplicationScoped
public class IdentityManager implements Serializable {

    private final IdentityService service;

    private final Authenticator authenticator;

    private final i18nResolver resolver;

    private final IdentityStore identityStore;

    @Inject
    public IdentityManager(IdentityService service, Authenticator authenticator, i18nResolver resolver, IdentityStore identityStore) {
        this.service = service;
        this.authenticator = authenticator;
        this.resolver = resolver;
        this.identityStore = identityStore;
    }

    public IdentityManager() {
        this(null, null, null, null);
    }

    public boolean authenticate(String email, String password) {
        EmailCredential credential = new EmailCredential(email, new Password(password));

        AuthenticationStatus authenticate = authenticator.authenticate(credential);

        if (authenticate == AuthenticationStatus.SUCCESS) {
            User user = findUserByEmail(email);
            identityStore.setUser(user);
        }

        return authenticate == AuthenticationStatus.SUCCESS;
    }

    public boolean authenticate(User user) {
        UsernamePasswordCredential credential = new UsernamePasswordCredential(user.getNickName(), user.getPassword());

        AuthenticationStatus authenticate = authenticator.authenticate(credential);

        if (authenticate == AuthenticationStatus.SUCCESS) {
            identityStore.setUser(user);
        }

        return authenticate == AuthenticationStatus.SUCCESS;
    }

    public void logout() {
        identityStore.setUser(null);
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
        return service.findUserByEmail(firstName, lastName, birthDate);
    }

    public User findUserByNickname(String nickname) {
        return service.findUserByNickName(nickname);
    }

    public User findUserByEmail(String email) {
        return service.findUserByEmail(email);
    }

    public Category findEverybody() {
        return service.findEveryBody();
    }

    public boolean hasRole(String role) {
        User user = getUser();
        if (Objects.nonNull(user)) {
            Set<Role> roles = user.getRoles();
            for (Role group : roles) {
                if (group.getName().equals(role)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public boolean hasRole(String[] roles) {
        for (String role : roles) {
            if (hasRole(role)) {
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

    public Language getLanguage() {
        return service.findLanguage(resolver.getLocale());
    }

    public void setLanguage(Locale language) {
        resolver.setLocale(language);
    }

    public User findUserByToken(String token) {
        return service.findUserByToken(token);
    }

}
