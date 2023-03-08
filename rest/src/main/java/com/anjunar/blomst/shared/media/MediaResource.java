package com.anjunar.blomst.shared.media;

import com.anjunar.common.filedisk.Media;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("shared/media")
public class MediaResource {

    private final EntityManager entityManager;

    @Inject
    public MediaResource(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public MediaResource() {
        this(null);
    }

    @Path("{id}")
    @GET
    public Response read(@PathParam("id") UUID id) {
        Media media = entityManager.find(Media.class, id);
        return Response.ok()
                .header("Content-Disposition", "attachment;filename=" + media.getName())
                .type(media.getType() + "/" + media.getSubType())
                .entity(media.getData())
                .build();

    }

}
