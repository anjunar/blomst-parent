package com.anjunar.common.security.enterprise;

import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.security.Role;
import com.anjunar.common.security.User;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;

import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class CustomIdentityStore implements IdentityStore {

    private final IdentityManager identity;

    private final Event<LoggedInEvent> event;

    @Inject
    public CustomIdentityStore(IdentityManager identity, Event<LoggedInEvent> event) {
        this.identity = identity;
        this.event = event;
    }

    public CustomIdentityStore() {
        this(null, null);
    }

    @Override
    public CredentialValidationResult validate(Credential credential) {
        CivilCredential civilCredential = (CivilCredential) credential;

        User user = identity.findUser(civilCredential.getFirstName(), civilCredential.getLastName(), civilCredential.getBirthdate());

        if (user != null) {
            if (user.getPassword().equals(civilCredential.getPasswordAsString())) {
                event.fire(new LoggedInEvent(user));

                Set<Role> relationships = user.getRoles();
                Set<String> roles = new HashSet<>();
                for (Role role : relationships) {
                    roles.add(role.getName());
                }
                return new CredentialValidationResult(user.getId().toString(), roles);
            }
        }
        return CredentialValidationResult.INVALID_RESULT;
    }

}
