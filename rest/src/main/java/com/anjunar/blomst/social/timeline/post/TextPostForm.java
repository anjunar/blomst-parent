package com.anjunar.blomst.social.timeline.post;

import com.anjunar.blomst.social.timeline.TextPost;
import com.anjunar.common.rest.mapper.annotations.MapperPolymorphism;

@MapperPolymorphism(TextPost.class)
public class TextPostForm extends AbstractPostForm {

    @Override
    public <E> E accept(AbstractPostFormVisitor<E> visitor) {
        return visitor.visit(this);
    }

}
