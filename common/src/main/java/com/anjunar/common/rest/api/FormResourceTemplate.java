package com.anjunar.common.rest.api;

import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import java.util.UUID;

public interface FormResourceTemplate<F extends AbstractRestEntity> {

    @Produces("application/json")
    @GET
    F read(@QueryParam("id") UUID id);

    @Consumes("application/json")
    @Produces("application/json")
    @POST
    F save(@Valid F form);

    @Consumes("application/json")
    @Produces("application/json")
    @PUT
    F update(@QueryParam("id") UUID id, @Valid F form);

    @DELETE
    ResponseOk delete(@QueryParam("id") UUID id);

}
