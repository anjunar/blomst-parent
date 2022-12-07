package com.anjunar.common.rest.mapper.annotations;

public interface MapperConverterType<E, D, C> {

    D factory(E entity);

    E updater(D dto, C context);

}
