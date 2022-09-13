package com.anjunar.common.i18n;

import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.security.enterprise.LoggedInEvent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.Serializable;
import java.util.Locale;

@RequestScoped
public class i18nResolver implements Serializable {

    private final HttpServletRequest request;

    private final HttpServletResponse response;

    private final IdentityManager identity;

    private final EntityManager entityManager;

    @Inject
    public i18nResolver(HttpServletRequest request, HttpServletResponse response, IdentityManager identity, EntityManager entityManager) {
        this.request = request;
        this.response = response;
        this.identity = identity;
        this.entityManager = entityManager;
    }

    public i18nResolver() {
        this(null, null, null, null);
    }

    public void setLocale(Locale locale) {
        response.setLocale(locale);
        if (identity.getUser() != null) {
            Language language = entityManager.createQuery("select l from Language l where l.locale = :locale", Language.class)
                    .setParameter("locale", locale)
                    .getSingleResult();
            identity.getUser().setLanguage(language);
        }
    }

    public Locale getLocale() {
        return response.getLocale();
    }

    public void onEvent(@Observes LoggedInEvent event) {
        response.setLocale(event.getUser().getLanguage().getLocale());
    }

}
