package com.anjunar.blomst.social.info.resume;

import com.anjunar.common.rest.search.AbstractRestSearch;
import com.anjunar.common.rest.search.RestPredicate;
import com.anjunar.common.rest.search.RestSort;
import com.anjunar.common.rest.search.provider.GenericManyToOneProvider;
import com.anjunar.common.rest.search.provider.GenericSortProvider;
import jakarta.ws.rs.QueryParam;

import java.util.List;
import java.util.UUID;

public class ResumeSearch extends AbstractRestSearch {

    @QueryParam("sort")
    @RestSort(GenericSortProvider.class)
    private List<String> sort;

    @QueryParam("owner")
    @RestPredicate(GenericManyToOneProvider.class)
    private UUID owner;

    public List<String> getSort() {
        return sort;
    }

    public void setSort(List<String> sort) {
        this.sort = sort;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }
}
