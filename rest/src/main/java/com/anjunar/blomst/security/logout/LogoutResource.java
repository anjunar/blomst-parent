package com.anjunar.blomst.security.logout;

import com.anjunar.common.rest.LinkDescription;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.blomst.ApplicationResource;

import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

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
