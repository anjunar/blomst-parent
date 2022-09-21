package com.anjunar.blomst.control.users.user.connections;

import com.anjunar.common.rest.search.AbstractRestSearch;
import com.anjunar.common.rest.search.RestPredicate;
import com.anjunar.common.rest.search.RestSort;
import com.anjunar.common.rest.search.provider.GenericManyToOneProvider;
import com.anjunar.common.rest.search.provider.GenericSortProvider;

import jakarta.ws.rs.QueryParam;
import java.util.List;
import java.util.UUID;

public class UserConnectionsSearch extends AbstractRestSearch {

    @RestSort(GenericSortProvider.class)
    @QueryParam("sort")
    private List<String> sort;

    @RestPredicate(GenericManyToOneProvider.class)
    @QueryParam("from")
    private UUID from;

    @RestPredicate(GenericManyToOneProvider.class)
    @QueryParam("category")
    private UUID category;

    @RestPredicate(GenericManyToOneProvider.class)
    @QueryParam("to")
    private UUID to;

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

    public UUID getCategory() {
        return category;
    }

    public void setCategory(UUID category) {
        this.category = category;
    }

    public UUID getTo() {
        return to;
    }

    public void setTo(UUID to) {
        this.to = to;
    }
}
