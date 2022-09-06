package com.anjunar.common.rest.mapper.entity;

import com.anjunar.common.ddd.AbstractEntity;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.mapper.annotations.MapperSecurity;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.introspector.bean.BeanProperty;
import jakarta.inject.Inject;

public class MapperSecurityProvider implements SecurityProvider {

    private final IdentityProvider identityProvider;

    @Inject
    public MapperSecurityProvider(IdentityProvider identityProvider) {
        this.identityProvider = identityProvider;
    }

    public MapperSecurityProvider() {
        this(null);
    }

    @Override
    public <S extends AbstractEntity, D extends AbstractRestEntity> boolean execute(S source, BeanProperty<S, ?> sourceProperty, D destination, BeanProperty<D, Object> destinationProperty) {
        MapperSecurity security = destinationProperty.getAnnotation(MapperSecurity.class);
        if (security == null) {
            return true;
        }
        for (String role : security.rolesAllowed()) {
            if (!identityProvider.hasRole(role)) {
                return false;
            }
        }
        return true;
    }
}
