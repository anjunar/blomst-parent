package com.anjunar.common.rest.schemamapper;

public interface Converter<E, D> {

    D factory(E entity);

    E updater(D dto);

}
