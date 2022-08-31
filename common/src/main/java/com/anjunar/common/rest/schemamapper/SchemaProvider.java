package com.anjunar.common.rest.schemamapper;

import com.anjunar.common.security.OwnerProvider;

public interface SchemaProvider {
    boolean execute(OwnerProvider entity, Class<?> aClass, String property);

}
