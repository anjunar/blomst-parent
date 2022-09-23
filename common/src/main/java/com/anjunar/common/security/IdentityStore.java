package com.anjunar.common.security;

import com.anjunar.common.i18n.Language;
import jakarta.enterprise.context.RequestScoped;

import java.util.Locale;
import java.util.Objects;
import java.util.Set;

@RequestScoped
public class IdentityStore {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Locale getLanguage() {
        User user = getUser();
        if (Objects.nonNull(user)) {
            return user.getLanguage().getLocale();
        }
        return Locale.forLanguageTag("en-DE");
    }

}
