package com.anjunar.common.rest.api;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.ws.rs.*;
import java.util.UUID;

public interface LoginResourceTemplate<F extends AbstractSchemaEntity> {
    @GET
    @Produces("application/json")
    @Path("login")
    F login();

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    @Path("login")
    ResponseOk login(@Valid F resource);

    @POST
    @Path("runas")
    @RolesAllowed({"Administrator"})
    ResponseOk runAs(@QueryParam("id") UUID id);
}
