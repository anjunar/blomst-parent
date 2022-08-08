package com.anjunar.common.rest.client.themoviedb;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import java.util.Locale;

@Path("3/search")
public interface Search {

    @GET
    @Path("movie")
    MovieSearchResponse movie(@QueryParam("api_key") String apiKey,
                              @QueryParam("language") Locale language,
                              @QueryParam("query") String query,
                              @QueryParam("page") Integer page,
                              @QueryParam("include_adult") Boolean includeAdult,
                              @QueryParam("region") String region,
                              @QueryParam("year") Integer year,
                              @QueryParam("primary_release_year") Integer primaryReleaseYear);

}
