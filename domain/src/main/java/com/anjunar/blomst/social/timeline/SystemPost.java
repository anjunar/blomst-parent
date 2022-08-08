package com.anjunar.blomst.social.timeline;

import org.hibernate.annotations.Filter;

import jakarta.persistence.Entity;

@Entity
@Filter(name = "deletedFilter", condition = "deleted = false")
public class SystemPost extends AbstractPost{

    private String hash;

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    @Override
    public <E> E accept(AbstractPostVisitor<E> visitor) {
        return visitor.visit(this);
    }

}
