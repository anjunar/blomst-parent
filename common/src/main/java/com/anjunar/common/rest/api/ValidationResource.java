package com.anjunar.common.rest.api;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

public interface ValidationResource<E extends AbstractRestEntity> {

    @Path("validate")
    @POST
    ResponseOk validate(E resource);

}
