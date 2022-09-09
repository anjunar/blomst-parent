package com.anjunar.common.rest.mapper.entity;

import com.anjunar.common.rest.api.AbstractSchemaEntity;
import com.anjunar.common.rest.mapper.annotations.MapperSecurity;
import com.anjunar.common.security.IdentityManager;
import com.anjunar.introspector.bean.BeanProperty;
import jakarta.inject.Inject;

public class MapperSecurityProvider implements SecurityProvider {

    private final IdentityManager identityManager;

    @Inject
    public MapperSecurityProvider(IdentityManager identityManager) {
        this.identityManager = identityManager;
    }

    public MapperSecurityProvider() {
        this(null);
    }

    @Override
    public <S, D extends AbstractSchemaEntity> boolean execute(S source, BeanProperty<S, ?> sourceProperty, D destination, BeanProperty<D, Object> destinationProperty) {
        MapperSecurity security = destinationProperty.getAnnotation(MapperSecurity.class);
        if (security == null) {
            return true;
        }
        for (String role : security.rolesAllowed()) {
            if (!identityManager.hasRole(role)) {
                return false;
            }
        }
        return true;
    }
}
