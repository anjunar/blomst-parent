package com.anjunar.common.rest.api;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

/**
 * @author Patrick Bittner on 01.08.17.
 */
public class Thumbnail extends AbstractRestEntity {

    @JsonInclude
    private String name;

    @JsonInclude
    private LocalDateTime lastModified;

    @JsonInclude
    private String data = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public void setLastModified(LocalDateTime lastModified) {
        this.lastModified = lastModified;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
