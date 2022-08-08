package com.anjunar.blomst.security.register;

import com.anjunar.common.filedisk.Image;
import com.anjunar.common.rest.LinkDescription;
import com.anjunar.common.security.IdentityProvider;
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
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Locale;

import static com.anjunar.common.rest.WebURLBuilderFactory.linkTo;
import static com.anjunar.common.rest.WebURLBuilderFactory.methodOn;

@Path("security/register")
@ApplicationScoped
public class RegisterResource {

    private static final Logger log = LoggerFactory.getLogger(RegisterResource.class);

    private final EntityManager entityManager;

    private final IdentityProvider identityProvider;

    @Inject
    public RegisterResource(EntityManager entityManager, IdentityProvider identityProvider) {
        this.entityManager = entityManager;
        this.identityProvider = identityProvider;
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
    @Transactional
    @LinkDescription("Do Registration")
    public Response register(RegisterForm resource) {

        User user = new User();

        user.setFirstName(resource.getFirstName());
        user.setLastName(resource.getLastName());
        user.setBirthDate(resource.getBirthDate());
        user.setPassword(resource.getPassword());
        user.setLanguage(Locale.forLanguageTag("en-DE"));
        user.setEnabled(true);

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
                .setParameter("role", "Guest")
                .getSingleResult();

        user.getRoles().add(userRole);

        identityProvider.authenticate(user);

        return Response.ok().build();
    }

}
