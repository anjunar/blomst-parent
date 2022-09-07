package com.anjunar.blomst.system;

import com.anjunar.blomst.system.languages.LanguagesResource;
import com.anjunar.blomst.system.languages.LanguagesSearch;
import com.anjunar.blomst.system.mail.TemplatesResource;
import com.anjunar.blomst.system.mail.TemplatesSearch;
import com.anjunar.common.rest.link.WebURLBuilder;
import com.anjunar.common.rest.link.WebURLBuilderFactory;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.Properties;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.*;

@Path("system")
public class SystemResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("Administrator")
    public SystemForm info() {

        SystemForm systemForm = new SystemForm();
        Properties properties = System.getProperties();
        systemForm.setProperties(properties);

        linkTo(methodOn(TemplatesResource.class).list(new TemplatesSearch()))
                .withRel("mail-templates")
                .build(systemForm::addLink);

        linkTo(methodOn(LanguagesResource.class).list(new LanguagesSearch()))
                .withRel("languages")
                .build(systemForm::addLink);

        return systemForm;
    }

}
