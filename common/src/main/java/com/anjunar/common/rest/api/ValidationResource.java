package com.anjunar.common.rest.api;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

public interface ValidationResource<E extends AbstractRestEntity> {

    @Path("validate")
    @POST
    ResponseOk validate(E resource);

}
