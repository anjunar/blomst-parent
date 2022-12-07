package com.anjunar.blomst.social.pages.page.likes;

import com.anjunar.blomst.shared.users.UserSelectSearch;
import com.anjunar.common.rest.search.RestPredicate;
import jakarta.ws.rs.QueryParam;

import java.util.UUID;

public class LikesSearch extends UserSelectSearch {

    @QueryParam("page")
    @RestPredicate(PageProvider.class)
    private UUID page;

    public UUID getPage() {
        return page;
    }

    public void setPage(UUID page) {
        this.page = page;
    }
}
