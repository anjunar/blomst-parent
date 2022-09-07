package com.anjunar.common.rest.mapper.rest;

import com.anjunar.common.ddd.AbstractEntity;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.AbstractSchemaEntity;
import com.anjunar.introspector.bean.BeanProperty;

public interface SecurityProvider {

    <S extends AbstractSchemaEntity, D> boolean execute(S source, BeanProperty<S, ?> sourceProperty, D destination, BeanProperty<D, Object> destinationProperty);

}
