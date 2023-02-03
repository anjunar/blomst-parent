package com.anjunar.common.i18n;

import com.anjunar.common.security.IdentityManager;
import jakarta.inject.Inject;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;
import java.util.Locale;

@WebFilter(filterName = "i18n", urlPatterns = "/service/*")
public class I18nFilter implements Filter {

    private final IdentityManager identityManager;

    @Inject
    public I18nFilter(IdentityManager identityManager) {
        this.identityManager = identityManager;
    }

    public I18nFilter() {
        this(null);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (identityManager.getUser() == null) {
            servletResponse.setLocale(Locale.forLanguageTag("en-DE"));
        } else {
            servletResponse.setLocale(identityManager.getUser().getLanguage().getLocale());
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
