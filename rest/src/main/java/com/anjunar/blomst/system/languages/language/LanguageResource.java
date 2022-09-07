package com.anjunar.blomst.system.languages.language;

import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.security.IdentityProvider;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

import java.util.Locale;

@Path("system/languages/language")
public class LanguageResource {

    private final IdentityProvider identityProvider;

    @Inject
    public LanguageResource(IdentityProvider identityProvider) {
        this.identityProvider = identityProvider;
    }

    public LanguageResource() {
        this(null);
    }

    @GET
    @Transactional
    @Path("lang")
    public ResponseOk language(@QueryParam("lang") Locale locale) {

        identityProvider.setLanguage(locale);

        return new ResponseOk();
    }

}
