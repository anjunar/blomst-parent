package com.anjunar.common.rest.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Patrick Bittner on 13.06.2015.
 */
public class Link {

    private final String url;

    private final String method;

    private final String rel;

    private final String description;

    @JsonCreator
    public Link(@JsonProperty("url") String url,
                @JsonProperty("method") String method,
                @JsonProperty("rel") String rel,
                @JsonProperty("description") String description) {
        this.url = url;
        this.method = method;
        this.rel = rel;
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public String getMethod() {
        return method;
    }

    public String getRel() {
        return rel;
    }

    public String getDescription() {
        return description;
    }
}
