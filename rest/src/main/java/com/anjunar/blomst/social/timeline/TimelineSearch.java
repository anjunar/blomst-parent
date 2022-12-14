package com.anjunar.blomst.social.timeline;

import com.anjunar.blomst.shared.likeable.AbstractLikeableSearch;
import com.anjunar.common.rest.search.RestPredicate;
import com.anjunar.common.rest.search.RestSort;
import com.anjunar.common.rest.search.provider.GenericManyToManyProvider;
import com.anjunar.common.rest.search.provider.GenericManyToOneProvider;
import com.anjunar.common.rest.search.provider.GenericSortProvider;

import com.anjunar.common.rest.search.provider.GenericTextProvider;
import jakarta.ws.rs.QueryParam;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class TimelineSearch extends AbstractLikeableSearch {

    @RestSort(GenericSortProvider.class)
    @QueryParam("sort")
    private List<String> sort;

    @QueryParam("text")
    @RestPredicate(TextProvider.class)
    private String text;

    @QueryParam("owner")
    @RestPredicate(GenericManyToOneProvider.class)
    private UUID owner;

    @QueryParam("source")
    @RestPredicate(GenericManyToManyProvider.class)
    private Set<UUID> source;

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<UUID> getSource() {
        return source;
    }

    public void setSource(Set<UUID> source) {
        this.source = source;
    }
}
