package com.anjunar.common.ddd;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractColumnRight<S> extends AbstractRight<S> {

    @Column(name = "source_column")
    private String column;

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

}
