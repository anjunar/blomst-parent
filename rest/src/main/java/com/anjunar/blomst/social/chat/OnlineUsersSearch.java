package com.anjunar.blomst.social.chat;

import com.anjunar.common.rest.search.AbstractRestSearch;
import com.anjunar.common.rest.search.RestSort;
import com.anjunar.common.rest.search.provider.GenericSortProvider;
import jakarta.ws.rs.QueryParam;

import java.util.List;

public class OnlineUsersSearch extends AbstractRestSearch {

    @QueryParam("sort")
    @RestSort(GenericSortProvider.class)
    private List<String> sort;

    public List<String> getSort() {
        return sort;
    }

    public void setSort(List<String> sort) {
        this.sort = sort;
    }
}
