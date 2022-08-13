package com.anjunar.common.rest.objectmapper;

public interface NewInstanceProvider {

    Object execute(Object id, Class sourceClass);

}
