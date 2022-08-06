package com.anjunar.blomst.social.timeline;


import org.hibernate.annotations.Filter;

import javax.persistence.Entity;

@Entity
@Filter(name = "deletedFilter", condition = "deleted = false")
public class TextPost extends AbstractPost {

    @Override
    public <E> E accept(AbstractPostVisitor<E> visitor) {
        return visitor.visit(this);
    }
}
