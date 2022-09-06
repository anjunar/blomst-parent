package com.anjunar.common.rest.mapper.annotations;

public interface MapperConverterType<E, D> {

    D factory(E entity);

    E updater(D dto);

}
