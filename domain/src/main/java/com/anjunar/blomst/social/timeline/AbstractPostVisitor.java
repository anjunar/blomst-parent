package com.anjunar.blomst.social.timeline;

public interface AbstractPostVisitor<E> {
    E visit(ImagePost post);

    E visit(VideoPost post);

    E visit(TextPost post);

}
