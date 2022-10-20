package com.anjunar.blomst.security.register;

import com.anjunar.common.filedisk.Image;
import com.anjunar.common.i18n.Language;
import com.anjunar.common.mail.Email;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.security.EmailType;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.security.Role;
import com.anjunar.common.security.User;
import com.anjunar.blomst.ApplicationResource;
import com.anjunar.blomst.control.users.user.UserForm;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Locale;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.linkTo;
import static com.anjunar.common.rest.link.WebURLBuilderFactory.methodOn;

@Path("security/register")
@ApplicationScoped
public class RegisterResource {

    private static final Logger log = LoggerFactory.getLogger(RegisterResource.class);

    private final EntityManager entityManager;

    private final IdentityManager identityManager;

    @Inject
    public RegisterResource(EntityManager entityManager, IdentityManager identityManager) {
        this.entityManager = entityManager;
        this.identityManager = identityManager;
    }

    public RegisterResource() {
        this(null, null);
    }

    @GET
    @Produces("application/json")
    @LinkDescription("Create Register")
    public RegisterForm register() {
        RegisterForm resource = new RegisterForm();

        linkTo(methodOn(RegisterResource.class).register(new RegisterForm()))
                .build(resource::addLink);

        linkTo(methodOn(ApplicationResource.class).validate(new UserForm()))
                .build(resource::addLink);

        return resource;
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @LinkDescription("Do Registration")
    public ResponseOk register(RegisterForm resource) {

        Language language = entityManager.createQuery("select l from Language l where l.locale = :locale", Language.class)
                .setParameter("locale", Locale.forLanguageTag("en-DE"))
                .getSingleResult();

        User user = new User();

        user.setPassword(resource.getPassword());
        user.setLanguage(language);
        user.setEnabled(true);

        EmailType email = new EmailType();
        email.setValue(resource.getEmail());
        user.getEmails().add(email);

        try {
            URL picture = getClass()
                    .getClassLoader()
                    .getResource("META-INF/resources/user.png");
            InputStream inputStream = picture.openStream();
            byte[] bytes = new byte[inputStream.available()];
            IOUtils.readFully(inputStream, bytes);
            Image image = new Image();
            image.setName("user.png");
            image.setData(bytes);
            image.setLastModified(LocalDateTime.now());
            image.setType("image");
            image.setSubType("png");
            user.setPicture(image);
        } catch (IOException e) {
            log.error(e.getLocalizedMessage());
        }

        entityManager.persist(user);

        Role userRole = entityManager.createQuery("select r from Role r where r.name = :role", Role.class)
                .setParameter("role", "User")
                .getSingleResult();

        user.getRoles().add(userRole);

        identityManager.authenticate(resource.getEmail(), resource.getPassword());

        ResponseOk response = new ResponseOk();

        linkTo(methodOn(ApplicationResource.class).service())
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }

}
