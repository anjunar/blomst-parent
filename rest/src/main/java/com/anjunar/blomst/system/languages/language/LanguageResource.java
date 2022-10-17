package com.anjunar.blomst.system.languages.language;

import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.security.IdentityManager;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

import java.util.Locale;

@Path("system/languages/language")
public class LanguageResource {

    private final IdentityManager identityManager;

    @Inject
    public LanguageResource(IdentityManager identityManager) {
        this.identityManager = identityManager;
    }

    public LanguageResource() {
        this(null);
    }

    @GET
    @Path("lang")
    @RolesAllowed({"Administrator", "User", "Guest"})
    public ResponseOk language(@QueryParam("lang") Locale locale) {

        identityManager.setLanguage(locale);

        return new ResponseOk();
    }

}
