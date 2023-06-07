package com.anjunar.blomst.shared.likes.likeable;

import com.anjunar.common.rest.mapper.annotations.MapperConverter;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AbstractLikeableRestEntity extends AbstractRestEntity {

    @JsonSchema(widget = JsonNode.Widget.NUMBER, title = "Views")
    private Integer views = 0;

    @JsonSchema(ignore = true)
    @MapperConverter(LikesConverter.class)
    @JsonProperty(required = true)
    private LikesType likes = new LikesType();

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public LikesType getLikes() {
        return likes;
    }

    public void setLikes(LikesType likes) {
        this.likes = likes;
    }
}
