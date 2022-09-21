package com.anjunar.blomst.social.timeline.post;

import com.anjunar.blomst.social.timeline.ImagePost;
import com.anjunar.blomst.social.timeline.SystemPost;
import com.anjunar.common.rest.mapper.annotations.MapperPolymorphism;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;

@MapperPolymorphism(SystemPost.class)
public class SystemPostForm extends AbstractPostForm{

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Hash Link")
    private String hash;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public <E> E accept(AbstractPostFormVisitor<E> visitor) {
        return visitor.visit(this);
    }

}
