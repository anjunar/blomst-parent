package com.anjunar.blomst.social.info.addresses.client.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Context {

    private String id;

    @JsonProperty("short_code")
    private String shortCode;

    private String wikidata;

    private String text;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getWikidata() {
        return wikidata;
    }

    public void setWikidata(String wikidata) {
        this.wikidata = wikidata;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
