package com.anjunar.blomst.social.timeline.post;

public interface AbstractPostFormVisitor<E> {
    E visit(VideoPostForm form);

    E visit(ImagePostForm form);

    E visit(TextPostForm form);

}
