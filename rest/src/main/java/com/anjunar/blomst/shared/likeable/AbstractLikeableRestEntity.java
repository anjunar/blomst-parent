package com.anjunar.blomst.shared.likeable;

import com.anjunar.blomst.control.users.user.UserForm;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.anjunar.common.rest.api.AbstractRestEntity;

import java.util.HashSet;
import java.util.Set;

public class AbstractLikeableRestEntity extends AbstractRestEntity {

    @JsonSchema(widget = JsonNode.Widget.NUMBER, title = "Views")
    private Integer views = 0;

    @JsonSchema(widget = JsonNode.Widget.LAZY_MULTI_SELECT, title = "Likes")
    private final Set<UserSelect> likes = new HashSet<>();

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Set<UserSelect> getLikes() {
        return likes;
    }

}
