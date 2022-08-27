package com.anjunar.common.rest.objectmapper;

public interface Converter<E, D> {

    D factory(E entity);

    E updater(D dto);

}
