package com.anjunar.blomst.shared.likeable;

import com.anjunar.common.rest.api.LongIntervall;
import com.anjunar.common.rest.search.AbstractRestSearch;
import com.anjunar.common.rest.search.RestPredicate;

import com.anjunar.common.rest.search.provider.GenericManyToManyProvider;
import com.anjunar.common.rest.search.provider.GenericNumberIntervallProvider;
import jakarta.ws.rs.QueryParam;
import java.util.Set;
import java.util.UUID;

public class AbstractLikeableSearch extends AbstractRestSearch {

    @QueryParam("views")
    @RestPredicate(GenericNumberIntervallProvider.class)
    private LongIntervall views;

    @QueryParam("likes")
    @RestPredicate(GenericManyToManyProvider.class)
    private Set<UUID> likes;

    public LongIntervall getViews() {
        return views;
    }

    public void setViews(LongIntervall views) {
        this.views = views;
    }

    public Set<UUID> getLikes() {
        return likes;
    }

    public void setLikes(Set<UUID> likes) {
        this.likes = likes;
    }
}
