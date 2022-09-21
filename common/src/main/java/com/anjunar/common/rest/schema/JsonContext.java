package com.anjunar.common.rest.schema;

import com.anjunar.introspector.bean.BeanProperty;

import java.util.HashSet;
import java.util.Set;

public class JsonContext {

    private final Set<BeanProperty<?, ?>> properties = new HashSet<>();

    public Set<BeanProperty<?, ?>> getProperties() {
        return properties;
    }
}
