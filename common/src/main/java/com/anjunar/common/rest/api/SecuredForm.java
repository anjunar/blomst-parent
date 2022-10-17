package com.anjunar.common.rest.api;

import com.anjunar.common.rest.mapper.annotations.MapperVisibility;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;

public class SecuredForm<E> extends AbstractSchemaEntity {

    @JsonSchema(widget = JsonNode.Widget.FORM, title = "Formular")
    @MapperVisibility
    private E form;

    public E getForm() {
        return form;
    }

    public void setForm(E form) {
        this.form = form;
    }

}
