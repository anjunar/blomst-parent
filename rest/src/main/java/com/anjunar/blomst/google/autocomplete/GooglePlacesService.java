package com.anjunar.blomst.google.autocomplete;

import com.anjunar.blomst.google.autocomplete.client.GooglePlacesAutocomplete;
import com.anjunar.blomst.google.autocomplete.client.PlacePredictions;
import com.anjunar.blomst.google.GoogleResource;
import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import java.util.Locale;

@ApplicationScoped
public class GooglePlacesService {

    private final static Logger log = LoggerFactory.getLogger(GooglePlacesService.class);

    public PlacePredictions find(String location, Locale locale) {
        if (StringUtils.isEmpty(location)) {
            return null;
        }
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://maps.googleapis.com/maps/api/place/queryautocomplete");
        ResteasyWebTarget webTarget = (ResteasyWebTarget) target;

        GooglePlacesAutocomplete service = webTarget.proxy(GooglePlacesAutocomplete.class);

        return service.execute(GoogleResource.API_KEY, location, locale.getLanguage());
    }

}
