package com.anjunar.common.rest.api;

import javax.ws.rs.*;

public interface ListResourceTemplate<R extends AbstractRestEntity, S> {

    @GET
    @Produces("application/json")
    Table<R> list(@BeanParam S search);

}
