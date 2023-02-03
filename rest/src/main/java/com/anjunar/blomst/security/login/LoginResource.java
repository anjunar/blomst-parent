package com.anjunar.blomst.security.login;

import com.anjunar.blomst.ApplicationResource;
import com.anjunar.common.rest.api.Form;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.security.User;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.*;

import java.util.UUID;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.linkTo;
import static com.anjunar.common.rest.link.WebURLBuilderFactory.methodOn;

@Path("security")
@RequestScoped
public class LoginResource {

    private final IdentityManager identityManager;

    private final EntityManager entityManager;


    @Inject
    public LoginResource(IdentityManager identityManager, EntityManager entityManager) {
        this.identityManager = identityManager;
        this.entityManager = entityManager;
    }

    public LoginResource() {
        this(null, null);
    }

    @GET
    @Produces("application/json")
    @Path("login")
    @LinkDescription("Create Login")
    public Form<LoginForm> login() {
        LoginForm loginForm = new LoginForm();
        Form<LoginForm> form = new Form<>(loginForm) {};

        linkTo(methodOn(LoginResource.class).login(new Form<>()))
                .build(form::addLink);

        return form;
    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("login")
    @LinkDescription("Do Login")
    public LoginResponse login(Form<LoginForm> resource) {
        identityManager.logout();

        User user = entityManager.createQuery("select u from User u join u.emails e where e.value = :email and u.password = :password ", User.class)
                .setParameter("email", resource.getForm().getEmail())
                .setParameter("password", resource.getForm().getPassword())
                .getSingleResult();

        if (identityManager.authenticate(user)) {

            entityManager.refresh(user);
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(user.getToken());

            linkTo(methodOn(ApplicationResource.class).service())
                    .withRel("redirect")
                    .build(loginResponse::addLink);

            return loginResponse;
        } else {
            throw new WebApplicationException(jakarta.ws.rs.core.Response.Status.FORBIDDEN);
        }
    }

    @POST
    @Path("runas")
    @RolesAllowed({"Administrator"})
    @LinkDescription("Run As")
    @Produces("application/json")
    public ResponseOk runAs(@QueryParam("id") UUID id) {
        User authenticate = identityManager.findUser(id);

        identityManager.logout();

        identityManager.authenticate(authenticate);

        ResponseOk response = new ResponseOk();

        linkTo(methodOn(ApplicationResource.class).service())
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }

}
