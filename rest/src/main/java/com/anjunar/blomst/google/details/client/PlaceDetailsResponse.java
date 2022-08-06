package com.anjunar.blomst.google.details.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaceDetailsResponse {

    private PlaceDetailsForm result;

    public PlaceDetailsForm getResult() {
        return result;
    }

    public void setResult(PlaceDetailsForm result) {
        this.result = result;
    }
}
