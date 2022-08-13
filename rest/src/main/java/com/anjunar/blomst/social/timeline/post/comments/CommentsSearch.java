package com.anjunar.blomst.social.timeline.post.comments;

import com.anjunar.blomst.shared.likeable.AbstractLikeableSearch;
import com.anjunar.common.rest.search.RestPredicate;
import com.anjunar.common.rest.search.RestSort;
import com.anjunar.common.rest.search.provider.GenericSortProvider;

import jakarta.ws.rs.QueryParam;
import java.util.List;
import java.util.UUID;

public class CommentsSearch extends AbstractLikeableSearch {

    @RestSort(GenericSortProvider.class)
    @QueryParam("sort")
    private List<String> sort;

    @RestPredicate(UserPostProvider.class)
    @QueryParam("post")
    private UUID post;

    @RestPredicate(ParentProvider.class)
    @QueryParam("parent")
    private UUID parent;

    @RestPredicate(TextProvider.class)
    @QueryParam("text")
    private String text;

    @QueryParam("owner")
    @RestPredicate(OwnerProvider.class)
    private UUID owner;

    public List<String> getSort() {
        return sort;
    }

    public void setSort(List<String> sort) {
        this.sort = sort;
    }

    public UUID getPost() {
        return post;
    }

    public void setPost(UUID post) {
        this.post = post;
    }

    public UUID getParent() {
        return parent;
    }

    public void setParent(UUID parent) {
        this.parent = parent;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

}
