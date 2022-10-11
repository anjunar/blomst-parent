package com.anjunar.blomst.social.info.address.client.resources;

import com.anjunar.blomst.social.info.address.client.resources.FeatureCollection;
import jakarta.ws.rs.*;

@Path("mapbox.places")
public interface MapBoxGeoCoding {
    @GET
    @Path("{query}.json")
    @Produces("application/vnd.geo+json;charset=utf-8")
    FeatureCollection execute(@QueryParam("access_token") String token,
                              @PathParam("query") String query,
                              @QueryParam("autocomplete") boolean autocomplete,
                              @QueryParam("fuzzyMatch") boolean fuzzyMatch,
                              @QueryParam("language") String language,
                              @QueryParam("types") String types
    );

}
