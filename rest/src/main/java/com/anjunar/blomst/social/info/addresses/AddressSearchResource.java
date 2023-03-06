package com.anjunar.blomst.social.info.addresses;

import com.anjunar.blomst.social.info.addresses.client.MapBoxService;
import com.anjunar.blomst.social.info.addresses.client.resources.Feature;
import com.anjunar.blomst.social.info.addresses.client.resources.FeatureCollection;
import com.anjunar.common.rest.api.SecuredForm;
import com.anjunar.common.rest.api.Table;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;

import java.util.ArrayList;
import java.util.List;

@Path("social/info/address/search")
public class AddressSearchResource {

    private final MapBoxService service;

    @Inject
    public AddressSearchResource(MapBoxService service) {
        this.service = service;
    }

    public AddressSearchResource() {
        this(null);
    }

    @GET
    @Produces("application/json")
    public Table<MapBoxAddress> list(@QueryParam("value") String query) {
        FeatureCollection featureCollection = service.geoCoding(query);

        List<MapBoxAddress> rows = new ArrayList<>();

        for (Feature feature : featureCollection.getFeatures()) {
            MapBoxAddress addressRow = new MapBoxAddress();
            addressRow.setName(feature.getPlaceName());
            MapBoxPoint point = new MapBoxPoint();
            point.setX(feature.getCenter().get(0));
            point.setY(feature.getCenter().get(1));
            addressRow.setPoint(point);
            rows.add(addressRow);
        }

        return new Table<>(rows, rows.size()) {};
    }

}
