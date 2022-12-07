package com.anjunar.blomst.social.timeline.post.comments.comment.likes;

import com.anjunar.blomst.shared.users.UserSelectSearch;
import com.anjunar.common.rest.search.RestPredicate;
import jakarta.ws.rs.QueryParam;

import java.util.UUID;

public class LikesSearch extends UserSelectSearch {

    @QueryParam("comment")
    @RestPredicate(CommentProvider.class)
    private UUID comment;

    public UUID getComment() {
        return comment;
    }

    public void setComment(UUID comment) {
        this.comment = comment;
    }
}
