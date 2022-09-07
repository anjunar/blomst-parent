package com.anjunar.common.i18n;

import com.anjunar.common.security.IdentityProvider;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;
import java.util.Locale;

@WebFilter("*")
public class I18nFilter implements Filter {

    private final IdentityProvider identityProvider;

    @Inject
    public I18nFilter(IdentityProvider identityProvider) {
        this.identityProvider = identityProvider;
    }

    public I18nFilter() {
        this(null);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
/*
        if (identityProvider.getUser() == null) {
            servletResponse.setLocale(Locale.forLanguageTag("en-DE"));
        } else {
            servletResponse.setLocale(identityProvider.getUser().getLanguage());
        }
*/
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
