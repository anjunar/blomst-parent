package com.anjunar.common.rest.api;

import java.util.UUID;

public class ResponseOk extends AbstractSchemaEntity {

    private UUID id;

    private Boolean ok = true;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Boolean getOk() {
        return ok;
    }

    public void setOk(Boolean ok) {
        this.ok = ok;
    }

}
