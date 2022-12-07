package com.anjunar.blomst.shared.likeable;

import com.anjunar.blomst.shared.Likeable;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.security.IdentityStore;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;

import java.util.UUID;

public abstract class AbstractLikeableResource {

    private final IdentityStore identityStore;

    public AbstractLikeableResource(IdentityStore identityStore) {
        this.identityStore = identityStore;
    }

    public AbstractLikeableResource() {
        this(null);
    }

    @POST
    @Produces("application/json")
    @Path("{id}/like")
    public ResponseOk like(@PathParam("id") UUID id) {
        Likeable likeable = load(id);
        likeable.getLikes().add(identityStore.getUser());
        return new ResponseOk();
    }

    @POST
    @Produces("application/json")
    @Path("{id}/dislike")
    public ResponseOk dislike(@PathParam("id") UUID id) {
        Likeable likeable = load(id);
        likeable.getLikes().remove(identityStore.getUser());
        return new ResponseOk();
    }

    protected abstract Likeable load(UUID id);

}
