package com.anjunar.common.rest.api.jaxrs;

import com.anjunar.common.rest.api.DateTimeDuration;
import com.anjunar.common.rest.api.DurationCreatedProvider;
import com.anjunar.common.rest.api.DurationModifiedProvider;

import jakarta.ws.rs.QueryParam;

public abstract class AbstractRestSearch {

    @QueryParam("index")
    private Integer index;

    @QueryParam("limit")
    private Integer limit;

    @QueryParam("created")
    @RestPredicate(DurationCreatedProvider.class)
    private DateTimeDuration created;

    @QueryParam("modified")
    @RestPredicate(DurationModifiedProvider.class)
    private DateTimeDuration modified;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public DateTimeDuration getCreated() {
        return created;
    }

    public void setCreated(DateTimeDuration created) {
        this.created = created;
    }

    public DateTimeDuration getModified() {
        return modified;
    }

    public void setModified(DateTimeDuration modified) {
        this.modified = modified;
    }
}
