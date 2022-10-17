package com.anjunar.blomst.social.info.addresses.client.resources;

import java.util.List;

public class FeatureCollection {

    private List<String> query;

    private List<Feature> features;

    public List<String> getQuery() {
        return query;
    }

    public void setQuery(List<String> query) {
        this.query = query;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(List<Feature> features) {
        this.features = features;
    }
}
