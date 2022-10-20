package com.anjunar.common.rest.mapper.entity;

import com.anjunar.common.ddd.AbstractEntity;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.AbstractSchemaEntity;
import com.anjunar.introspector.bean.BeanProperty;

public interface SecurityProvider {

    <S, D> boolean execute(S source, BeanProperty<S, ?> sourceProperty, D destination, BeanProperty<D, Object> destinationProperty);

}
