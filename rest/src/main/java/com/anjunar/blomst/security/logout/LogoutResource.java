package com.anjunar.blomst.security.logout;

import com.anjunar.common.rest.LinkDescription;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.blomst.ApplicationResource;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static com.anjunar.common.rest.WebURLBuilderFactory.linkTo;
import static com.anjunar.common.rest.WebURLBuilderFactory.methodOn;

@Path("security/logout")
@ApplicationScoped
public class LogoutResource {

    private final IdentityProvider identityProvider;

    @Inject
    public LogoutResource(IdentityProvider identityProvider) {
        this.identityProvider = identityProvider;
    }

    public LogoutResource() {
        this(null);
    }

    @GET
    @Produces("application/json")
    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Create Logout")
    public LogoutForm logout1() {
        LogoutForm resource = new LogoutForm();

        linkTo(methodOn(LogoutResource.class).logout())
                .build(resource::addLink);

        return resource;
    }

    @POST
    @Produces("application/json")
    @RolesAllowed({"Administrator", "User", "Guest"})
    @LinkDescription("Do Logout")
    public LogoutForm logout() {

        identityProvider.logout();

        LogoutForm resource = new LogoutForm();

        linkTo(methodOn(ApplicationResource.class).service())
                .withRel("redirect")
                .build(resource::addLink);

        return resource;
    }

}
