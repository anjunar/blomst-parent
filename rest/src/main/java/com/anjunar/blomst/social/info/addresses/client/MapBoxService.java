package com.anjunar.blomst.social.info.addresses.client;

import com.anjunar.blomst.social.info.addresses.client.resources.FeatureCollection;
import com.anjunar.blomst.social.info.addresses.client.resources.MapBoxGeoCoding;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.plugins.providers.jackson.ResteasyJackson2Provider;

public class MapBoxService {

    public FeatureCollection geoCoding(String query) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("https://api.mapbox.com/geocoding/v5");
        ResteasyWebTarget webTarget = (ResteasyWebTarget) target;

        ResteasyJackson2Provider resteasyJacksonProvider = new ResteasyJackson2Provider() {};
        ObjectMapper mapper = new ObjectMapper()
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        resteasyJacksonProvider.setMapper(mapper);

        webTarget.register(resteasyJacksonProvider);
        MapBoxGeoCoding service = webTarget.proxy(MapBoxGeoCoding.class);

        return service.execute(MapBoxKey.token, query, false, false, "de","address");
    }

}
