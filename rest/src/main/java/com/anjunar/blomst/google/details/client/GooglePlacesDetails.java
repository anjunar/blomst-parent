package com.anjunar.blomst.google.details.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;

public interface GooglePlacesDetails {

    @GET
    @Path("json")
    @Produces("application/json")
    PlaceDetailsResponse execute(@QueryParam("key") String key, @QueryParam("placeid") String placeId, @QueryParam("language") String language);

}
