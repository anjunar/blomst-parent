package com.anjunar.blomst.social.timeline.post.likes;

import com.anjunar.blomst.shared.users.UserSelectSearch;
import com.anjunar.common.rest.search.RestPredicate;
import jakarta.ws.rs.QueryParam;

import java.util.UUID;

public class LikesSearch extends UserSelectSearch {

    @QueryParam("post")
    @RestPredicate(PostProvider.class)
    private UUID post;

    public UUID getPost() {
        return post;
    }

    public void setPost(UUID post) {
        this.post = post;
    }
}
