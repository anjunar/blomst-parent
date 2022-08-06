package com.anjunar.blomst.security.login;

import com.anjunar.common.rest.LinkDescription;
import com.anjunar.common.rest.api.LoginResourceTemplate;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.security.User;
import com.anjunar.blomst.ApplicationResource;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import java.util.UUID;

import static com.anjunar.common.rest.WebURLBuilderFactory.*;

@Path("security")
@RequestScoped
public class LoginResource implements LoginResourceTemplate<LoginForm> {

    private final IdentityProvider identityProvider;

    @Inject
    public LoginResource(IdentityProvider identityProvider) {
        this.identityProvider = identityProvider;
    }

    public LoginResource() {
        this(null);
    }

    @Override
    @GET
    @Produces("application/json")
    @Path("login")
    @LinkDescription("Create Login")
    public LoginForm login() {
        LoginForm loginForm = new LoginForm();

        linkTo(methodOn(LoginResource.class).login(new LoginForm()))
                .build(loginForm::addLink);

        return loginForm;
    }

    @Override
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("login")
    @LinkDescription("Do Login")
    public ResponseOk login(@Valid LoginForm resource) {

        User user = identityProvider.findUser(resource.getFirstname(), resource.getLastname(), resource.getBirthday());

        if (identityProvider.authenticate(user)) {
            linkTo(methodOn(ApplicationResource.class).service())
                    .withRel("redirect")
                    .build(resource::addLink);

            return new ResponseOk();
        } else {
            throw new WebApplicationException(javax.ws.rs.core.Response.Status.FORBIDDEN);
        }
    }

    @Override
    @POST
    @Path("runas")
    @RolesAllowed({"Administrator"})
    @LinkDescription("Run As")
    public ResponseOk runAs(@QueryParam("id") UUID id) {
        User authenticate = identityProvider.findUser(id);

        identityProvider.logout();

        identityProvider.authenticate(authenticate);

        return new ResponseOk();
    }

}
