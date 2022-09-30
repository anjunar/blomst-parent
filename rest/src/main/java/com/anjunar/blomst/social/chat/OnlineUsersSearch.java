package com.anjunar.blomst.social.chat;

import com.anjunar.common.rest.search.AbstractRestSearch;
import com.anjunar.common.rest.search.RestPredicate;
import com.anjunar.common.rest.search.RestSort;
import com.anjunar.common.rest.search.provider.GenericSortProvider;
import jakarta.ws.rs.QueryParam;

import java.util.List;
import java.util.UUID;

public class OnlineUsersSearch extends AbstractRestSearch {

    @QueryParam("sort")
    @RestSort(GenericSortProvider.class)
    private List<String> sort;

    @QueryParam("from")
    @RestPredicate(FromProvider.class)
    private UUID from;

    public List<String> getSort() {
        return sort;
    }

    public void setSort(List<String> sort) {
        this.sort = sort;
    }

    public UUID getFrom() {
        return from;
    }

    public void setFrom(UUID from) {
        this.from = from;
    }
}
