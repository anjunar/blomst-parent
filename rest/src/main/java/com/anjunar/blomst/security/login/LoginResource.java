package com.anjunar.blomst.security.login;

import com.anjunar.blomst.ApplicationResource;
import com.anjunar.common.rest.api.Form;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.LoginResourceTemplate;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.common.security.User;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;

import java.util.UUID;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.linkTo;
import static com.anjunar.common.rest.link.WebURLBuilderFactory.methodOn;

@Path("security")
@RequestScoped
public class LoginResource implements LoginResourceTemplate<Form<LoginForm>> {

    private final IdentityManager identityManager;

    @Inject
    public LoginResource(IdentityManager identityManager) {
        this.identityManager = identityManager;
    }

    public LoginResource() {
        this(null);
    }

    @Override
    @GET
    @Produces("application/json")
    @Path("login")
    @LinkDescription("Create Login")
    public Form<LoginForm> login() {
        LoginForm loginForm = new LoginForm();

        linkTo(methodOn(LoginResource.class).login(new Form<>()))
                .build(loginForm::addLink);

        return new Form<>(loginForm) {};
    }

    @Override
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("login")
    @LinkDescription("Do Login")
    public ResponseOk login(Form<LoginForm> resource) {
        identityManager.logout();

        if (identityManager.authenticate(resource.getForm().getEmail(), resource.getForm().getPassword())) {
            ResponseOk response = new ResponseOk();

            linkTo(methodOn(ApplicationResource.class).service())
                    .withRel("redirect")
                    .build(response::addLink);

            return response;
        } else {
            throw new WebApplicationException(jakarta.ws.rs.core.Response.Status.FORBIDDEN);
        }
    }

    @Override
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
