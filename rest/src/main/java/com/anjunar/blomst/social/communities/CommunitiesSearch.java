package com.anjunar.blomst.social.communities;

import com.anjunar.common.rest.search.AbstractRestSearch;
import com.anjunar.common.rest.search.RestPredicate;
import com.anjunar.common.rest.search.RestSort;
import com.anjunar.common.rest.search.provider.GenericNameProvider;
import com.anjunar.common.rest.search.provider.GenericSortProvider;

import jakarta.ws.rs.QueryParam;
import java.util.List;

public class CommunitiesSearch extends AbstractRestSearch {

    @QueryParam("sort")
    @RestSort(GenericSortProvider.class)
    private List<String> sort;

    @QueryParam("name")
    @RestPredicate(GenericNameProvider.class)
    private String name;

    public List<String> getSort() {
        return sort;
    }

    public void setSort(List<String> sort) {
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
