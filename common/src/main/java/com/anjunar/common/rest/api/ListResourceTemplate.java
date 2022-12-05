package com.anjunar.common.rest.api;

import jakarta.ws.rs.*;

public interface ListResourceTemplate<R, S> {

    @GET
    @Produces("application/json")
    Table<R> list(@BeanParam S search);

}
