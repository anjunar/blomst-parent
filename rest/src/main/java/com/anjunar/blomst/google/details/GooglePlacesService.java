package com.anjunar.blomst.google.details;

import com.anjunar.blomst.google.details.client.GooglePlacesDetails;
import com.anjunar.blomst.google.details.client.PlaceDetailsForm;
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


    public PlaceDetailsForm findDetails(String id, Locale locale) {
        if (StringUtils.isEmpty(id)) {
            return null;
        }
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://maps.googleapis.com/maps/api/place/details");
        ResteasyWebTarget rtarget = (ResteasyWebTarget) target;

        GooglePlacesDetails service = rtarget.proxy(GooglePlacesDetails.class);
        return service.execute(GoogleResource.API_KEY, id, locale.getLanguage()).getResult();
    }
}