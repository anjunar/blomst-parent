package com.anjunar.common.rest.api;

import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.*;

public abstract class AbstractRestEntity implements RestEntity, LinksContainer {

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Id", id = true, readOnly = true)
    private UUID id;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    @JsonSchema(widget = JsonNode.Widget.DATETIME_LOCAL, title = "Created", readOnly = true)
    private LocalDateTime created;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    @JsonSchema(widget = JsonNode.Widget.DATETIME_LOCAL, title = "Modified", readOnly = true)
    private LocalDateTime modified;

    @JsonSchema(ignore = true)
    private final Map<String, Link> links = new LinkedHashMap<>();

    public AbstractRestEntity(UUID id, LocalDateTime created, LocalDateTime modified) {
        this.id = id;
        this.created = created;
        this.modified = modified;
    }

    public AbstractRestEntity() {
        super();
    }

    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    public Map<String, Link> getLinks() {
        return links;
    }

    @Override
    public void addLink(String rel, Link link) {
        links.put(rel, link);
    }
}
