package com.anjunar.common.ddd;

public class AbstractOnEntity {

    private AbstractEntity entity;

    public AbstractOnEntity(AbstractEntity entity) {
        this.entity = entity;
    }

    public AbstractEntity getEntity() {
        return entity;
    }

}
