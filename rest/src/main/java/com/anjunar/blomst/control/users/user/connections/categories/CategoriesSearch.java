package com.anjunar.blomst.control.users.user.connections.categories;

import com.anjunar.common.rest.api.jaxrs.AbstractRestSearch;
import com.anjunar.common.rest.api.jaxrs.RestPredicate;
import com.anjunar.common.rest.api.jaxrs.RestSort;
import com.anjunar.common.rest.api.jaxrs.provider.GenericSortProvider;

import jakarta.ws.rs.QueryParam;
import java.util.List;
import java.util.UUID;

public class CategoriesSearch extends AbstractRestSearch {

    @RestSort(GenericSortProvider.class)
    @QueryParam("sort")
    private List<String> sort;

    @RestPredicate(NameProvider.class)
    @QueryParam("name")
    private String name;

    @RestPredicate(OwnerProvider.class)
    @QueryParam("owner")
    private UUID owner;

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

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }
}
