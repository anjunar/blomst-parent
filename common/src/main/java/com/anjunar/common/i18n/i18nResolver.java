package com.anjunar.common.i18n;

import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.security.enterprise.LoggedInEvent;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.Serializable;
import java.util.Locale;

@RequestScoped
public class i18nResolver implements Serializable {

    private final HttpServletRequest request;

    private final HttpServletResponse response;

    private final IdentityManager identity;

    @Inject
    public i18nResolver(HttpServletRequest request, HttpServletResponse response, IdentityManager identity) {
        this.request = request;
        this.response = response;
        this.identity = identity;
    }

    public i18nResolver() {
        this(null, null, null);
    }

    public void setLocale(Locale locale) {
        response.setLocale(locale);
        if (identity.getUser() != null) {
            identity.getUser().setLanguage(locale);
        }
    }

    public Locale getLocale() {
        return response.getLocale();
    }

    public void onEvent(@Observes LoggedInEvent event) {
        response.setLocale(event.getUser().getLanguage());
    }

}
