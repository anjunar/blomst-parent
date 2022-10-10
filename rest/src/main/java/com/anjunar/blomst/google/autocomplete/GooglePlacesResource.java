package com.anjunar.blomst.google.autocomplete;

import com.anjunar.blomst.google.autocomplete.client.PlacePredictionForm;
import com.anjunar.blomst.google.autocomplete.client.PlacePredictions;
import com.anjunar.common.rest.api.Table;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("generic/google")
@ApplicationScoped
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GooglePlacesResource {

    private final GooglePlacesService service;

    @Inject
    public GooglePlacesResource(GooglePlacesService service) {
        this.service = service;
    }

    public GooglePlacesResource() {
        this(null);
    }

    @GET
    @Path("place/autocomplete")
    public Table<LocationForm> geoCoding(@Context HttpServletRequest request, @QueryParam("address") String address) {
        PlacePredictions placePredictions = service.find(address, request.getLocale());
        List<LocationForm> forms = new ArrayList<>();
        if (placePredictions == null) {
            return new Table<>(forms, forms.size());
        }

        for (PlacePredictionForm placePrediction : placePredictions.getPredictions()) {
            LocationForm form = new LocationForm();
            form.setName(placePrediction.getDescription());
            form.setId(placePrediction.getPlace_id());
            forms.add(form);
        }

        return new Table<>(forms, forms.size()) {};
    }

}
