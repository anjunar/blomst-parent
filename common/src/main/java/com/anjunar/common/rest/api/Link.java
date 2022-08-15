package com.anjunar.common.rest.api;

import com.anjunar.common.rest.link.LinkType;
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
    private final LinkType type;

    @JsonCreator
    public Link(@JsonProperty("url") String url,
                @JsonProperty("method") String method,
                @JsonProperty("rel") String rel,
                @JsonProperty("description") String description,
                @JsonProperty("type") LinkType type) {
        this.url = url;
        this.method = method;
        this.rel = rel;
        this.description = description;
        this.type = type;
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

    public LinkType getType() {
        return type;
    }
}
