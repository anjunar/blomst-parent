package com.anjunar.blomst.shared.likes;

import com.anjunar.blomst.shared.users.UserSelectSearch;
import com.anjunar.common.rest.search.RestPredicate;
import jakarta.ws.rs.QueryParam;

import java.util.UUID;

public class LikesSearch extends UserSelectSearch {

    @QueryParam("post")
    @RestPredicate(PostProvider.class)
    private UUID post;

    @QueryParam("page")
    @RestPredicate(PageProvider.class)
    private UUID page;

    @QueryParam("comment")
    @RestPredicate(CommentProvider.class)
    private UUID comment;

    public UUID getPost() {
        return post;
    }

    public void setPost(UUID post) {
        this.post = post;
    }

    public UUID getPage() {
        return page;
    }

    public void setPage(UUID page) {
        this.page = page;
    }

    public UUID getComment() {
        return comment;
    }

    public void setComment(UUID comment) {
        this.comment = comment;
    }
}
