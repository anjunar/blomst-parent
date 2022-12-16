package com.anjunar.blomst.shared.site;

import com.anjunar.blomst.shared.Alternative;
import com.anjunar.common.rest.mapper.annotations.MapperConverterType;

public class StringConverter implements MapperConverterType<Alternative, String, SiteSelect> {
    @Override
    public String factory(Alternative entity) {
        return entity.getValue();
    }

    @Override
    public Alternative updater(String dto, SiteSelect context) {
        return null;
    }
}
