package com.anjunar.common.rest.api;

import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.annotations.JsonSchemaReadOnly;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class AbstractRestEntity extends AbstractSchemaEntity implements RestEntity, LinksContainer {

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Id", id = true)
    @JsonSchemaReadOnly
    private UUID id;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    @JsonSchema(widget = JsonNode.Widget.DATETIME_LOCAL, title = "Created")
    @JsonSchemaReadOnly
    private LocalDateTime created;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "UTC")
    @JsonSchema(widget = JsonNode.Widget.DATETIME_LOCAL, title = "Modified")
    @JsonSchemaReadOnly
    private LocalDateTime modified;

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

}
