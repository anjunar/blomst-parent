package com.anjunar.blomst.shared.users;

import com.anjunar.common.rest.api.jaxrs.AbstractRestSearch;
import com.anjunar.common.rest.api.jaxrs.RestPredicate;
import com.anjunar.common.rest.api.jaxrs.RestSort;
import com.anjunar.common.rest.api.jaxrs.provider.GenericSortProvider;

import jakarta.ws.rs.QueryParam;
import java.util.List;

public class UserSelectSearch extends AbstractRestSearch {

    @QueryParam("sort")
    @RestSort(GenericSortProvider.class)
    private List<String> sort;

    @RestPredicate(NamingProvider.class)
    @QueryParam("naming")
    private String naming;

    public List<String> getSort() {
        return sort;
    }

    public void setSort(List<String> sort) {
        this.sort = sort;
    }

    public String getNaming() {
        return naming;
    }

    public void setNaming(String naming) {
        this.naming = naming;
    }

}
