package com.anjunar.blomst.google.autocomplete.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;

public interface GooglePlacesAutocomplete {

    @GET
    @Path("json")
    @Produces("application/json")
    PlacePredictions execute(@QueryParam("key") String apiKey, @QueryParam("input") String input, @QueryParam("language") String language);
}
