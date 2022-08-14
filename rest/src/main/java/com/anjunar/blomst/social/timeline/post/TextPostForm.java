package com.anjunar.blomst.social.timeline.post;

public class TextPostForm extends AbstractPostForm {

    @Override
    public <E> E accept(AbstractPostFormVisitor<E> visitor) {
        return visitor.visit(this);
    }

}
