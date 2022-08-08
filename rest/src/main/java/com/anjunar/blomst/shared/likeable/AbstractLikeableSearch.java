package com.anjunar.blomst.shared.likeable;

import com.anjunar.common.rest.api.jaxrs.AbstractRestSearch;
import com.anjunar.common.rest.api.jaxrs.RestPredicate;

import jakarta.ws.rs.QueryParam;
import java.util.Set;
import java.util.UUID;

public class AbstractLikeableSearch extends AbstractRestSearch {

    @QueryParam("views")
    private Integer views;

    @QueryParam("likes")
    @RestPredicate(LikesProvider.class)
    private Set<UUID> likes;

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Set<UUID> getLikes() {
        return likes;
    }

    public void setLikes(Set<UUID> likes) {
        this.likes = likes;
    }
}
