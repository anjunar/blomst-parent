package com.anjunar.blomst.social.pages.page;

import com.anjunar.common.rest.api.AbstractRestEntity;

import java.time.LocalDateTime;

public class ImageResource extends AbstractRestEntity {

    private String name;

    private LocalDateTime lastModified;

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

}
