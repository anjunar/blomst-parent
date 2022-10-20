package com.anjunar.common.rest.mapper.entity;

import com.anjunar.common.rest.api.AbstractSchemaEntity;
import com.anjunar.common.rest.mapper.annotations.MapperSecurity;
import com.anjunar.common.security.IdentityStore;
import com.anjunar.common.security.OwnerProvider;
import com.anjunar.introspector.bean.BeanProperty;
import jakarta.inject.Inject;

public class MapperSecurityProvider implements SecurityProvider {

    private final IdentityStore identityStore;

    @Inject
    public MapperSecurityProvider(IdentityStore identityStore) {
        this.identityStore = identityStore;
    }

    public MapperSecurityProvider() {
        this(null);
    }

    @Override
    public <S, D> boolean execute(S source, BeanProperty<S, ?> sourceProperty, D destination, BeanProperty<D, Object> destinationProperty) {
        MapperSecurity security = destinationProperty.getAnnotation(MapperSecurity.class);
        if (security == null) {
            return true;
        }
        if (source instanceof OwnerProvider ownerProvider) {
            if (ownerProvider.getOwner().equals(identityStore.getUser())) {
                return true;
            }
        }
        for (String role : security.rolesAllowed()) {
            if (!identityStore.hasRole(role)) {
                return false;
            }
        }
        return true;
    }
}
