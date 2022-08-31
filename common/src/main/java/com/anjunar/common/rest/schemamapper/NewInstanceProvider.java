package com.anjunar.common.rest.schemamapper;

public interface NewInstanceProvider {

    Object execute(Object id, Class sourceClass);

}
