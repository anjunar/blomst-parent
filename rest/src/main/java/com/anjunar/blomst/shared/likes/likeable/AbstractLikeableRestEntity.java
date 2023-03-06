package com.anjunar.blomst.shared.likes.likeable;

import com.anjunar.common.rest.mapper.annotations.MapperConverter;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.anjunar.common.rest.api.AbstractRestEntity;

public class AbstractLikeableRestEntity extends AbstractRestEntity {

    @JsonSchema(widget = JsonNode.Widget.NUMBER, title = "Views")
    private Integer views = 0;

    @JsonSchema(widget = JsonNode.Widget.LIKE, title = "Likes")
    @MapperConverter(LikesConverter.class)
    private boolean likes = false;

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public boolean isLikes() {
        return likes;
    }

    public void setLikes(boolean likes) {
        this.likes = likes;
    }
}
