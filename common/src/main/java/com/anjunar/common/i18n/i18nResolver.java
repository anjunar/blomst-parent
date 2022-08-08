package com.anjunar.common.i18n;

import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.security.enterprise.LoggedInEvent;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import java.io.Serializable;
import java.util.Locale;

@RequestScoped
public class i18nResolver implements Serializable {

    private final HttpServletRequest request;

    private final HttpServletResponse response;

    private final IdentityProvider identity;

    @Inject
    public i18nResolver(HttpServletRequest request, HttpServletResponse response, IdentityProvider identity) {
        this.request = request;
        this.response = response;
        this.identity = identity;
    }

    public i18nResolver() {
        this(null, null, null);
    }

    @Transactional
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

    public void onResponse(@Observes ServletResponse response) {
        if (identity.getUser() == null) {
            response.setLocale(Locale.forLanguageTag("en-DE"));
        } else {
            response.setLocale(identity.getUser().getLanguage());
        }
    }

}
