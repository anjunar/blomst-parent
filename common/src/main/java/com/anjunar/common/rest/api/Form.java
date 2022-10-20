package com.anjunar.common.rest.api;

public class Form<E> extends AbstractSchemaEntity {

    private E form;

    public Form(E form) {
        this.form = form;
    }

    public Form() {
        this(null);
    }

    public E getForm() {
        return form;
    }

    public void setForm(E form) {
        this.form = form;
    }
}
