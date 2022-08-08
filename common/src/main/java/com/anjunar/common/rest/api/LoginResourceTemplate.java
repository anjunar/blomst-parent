package com.anjunar.common.rest.api;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
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
