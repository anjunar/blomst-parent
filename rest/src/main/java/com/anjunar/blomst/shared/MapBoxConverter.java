package com.anjunar.blomst.shared;

import com.anjunar.blomst.social.info.addresses.MapBoxAddress;
import com.anjunar.common.rest.mapper.annotations.MapperConverterType;

public class MapBoxConverter implements MapperConverterType<String, MapBoxAddress, Object> {
    @Override
    public MapBoxAddress factory(String entity) {
        MapBoxAddress address = new MapBoxAddress();
        address.setName(entity);
        return address;
    }

    @Override
    public String updater(MapBoxAddress dto, Object context) {
        return dto.getName();
    }
}
