package com.anjunar.blomst.social.communities.community.connections;

import com.anjunar.common.rest.api.jaxrs.AbstractRestSearch;
import com.anjunar.common.rest.api.jaxrs.RestPredicate;
import com.anjunar.common.rest.api.jaxrs.RestSort;
import com.anjunar.common.rest.api.jaxrs.provider.GenericSortProvider;
import com.anjunar.blomst.social.communities.Status;

import jakarta.ws.rs.QueryParam;
import java.util.List;
import java.util.UUID;

public class CommunityConnectionsSearch extends AbstractRestSearch {

    @RestSort(GenericSortProvider.class)
    @QueryParam("sort")
    private List<String> sort;

    @RestPredicate(FromProvider.class)
    @QueryParam("from")
    private UUID from;

    @RestPredicate(StatusProvider.class)
    @QueryParam("status")
    private Status status;

    @RestPredicate(RoleProvider.class)
    @QueryParam("role")
    private UUID role;

    @RestPredicate(ToProvider.class)
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public UUID getRole() {
        return role;
    }

    public void setRole(UUID role) {
        this.role = role;
    }

    public UUID getTo() {
        return to;
    }

    public void setTo(UUID to) {
        this.to = to;
    }
}
