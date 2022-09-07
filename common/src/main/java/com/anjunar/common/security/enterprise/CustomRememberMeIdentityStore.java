package com.anjunar.common.security.enterprise;

import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.security.Role;
import com.anjunar.common.security.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.security.enterprise.CallerPrincipal;
import jakarta.security.enterprise.credential.RememberMeCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.RememberMeIdentityStore;
import jakarta.transaction.Transactional;
import java.security.Key;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@ApplicationScoped
public class CustomRememberMeIdentityStore implements RememberMeIdentityStore {

    private final IdentityProvider identity;

    @Inject
    public CustomRememberMeIdentityStore(IdentityProvider identity) {
        this.identity = identity;
    }

    public CustomRememberMeIdentityStore() {
        this(null);
    }

    @Override
    @Transactional
    public CredentialValidationResult validate(RememberMeCredential credential) {
        User user = identity.findUserByToken(credential.getToken());

        if (user == null) {
            return CredentialValidationResult.INVALID_RESULT;
        }

        Set<Role> userRoles = user.getRoles();
        Set<String> roles = new HashSet<>();
        for (Role role : userRoles) {
            roles.add(role.getName());
        }

        return new CredentialValidationResult(user.getId().toString(), roles);
    }

    @Override
    public String generateLoginToken(CallerPrincipal callerPrincipal, Set<String> groups) {
        User user = identity.findUser(UUID.fromString(callerPrincipal.getName()));
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        String compact = Jwts.builder().setSubject(callerPrincipal.getName()).signWith(key).compact();
        user.setToken(compact);
        return compact;
    }

    @Override
    public void removeLoginToken(String token) {
        User user = identity.findUserByToken(token);
        user.setToken("");
    }
}
