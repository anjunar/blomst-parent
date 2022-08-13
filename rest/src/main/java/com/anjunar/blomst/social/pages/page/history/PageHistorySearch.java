package com.anjunar.blomst.social.pages.page.history;

import com.anjunar.common.rest.search.AbstractRestSearch;

import jakarta.ws.rs.QueryParam;
import java.util.UUID;

public class PageHistorySearch extends AbstractRestSearch {

    @QueryParam("id")
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
