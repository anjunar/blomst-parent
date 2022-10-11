package com.anjunar.blomst.social.info.address;

import com.anjunar.blomst.control.users.Addresses;
import com.anjunar.blomst.control.users.user.UserResource;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.common.rest.api.FormResourceTemplate;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import com.anjunar.common.rest.mapper.ResourceRestMapper;
import com.anjunar.common.rest.schema.schema.JsonObject;
import com.anjunar.common.security.IdentityManager;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;

import java.util.UUID;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.*;

@ApplicationScoped
@Path("social/info/addresses")
public class AddressesResource implements FormResourceTemplate<AddressesForm> {

    private final ResourceEntityMapper entityMapper;

    private final ResourceRestMapper restMapper;

    private final EntityManager entityManager;

    private final IdentityManager identityManager;

    @Inject
    public AddressesResource(ResourceEntityMapper entityMapper, ResourceRestMapper restMapper, EntityManager entityManager, IdentityManager identityManager) {
        this.entityMapper = entityMapper;
        this.restMapper = restMapper;
        this.entityManager = entityManager;
        this.identityManager = identityManager;
    }

    public AddressesResource() {
        this(null, null, null, null);
    }

    @Path("create")
    @GET
    @Produces("application/json")
    public AddressesForm create() {
        AddressesForm form = new AddressesForm();

        form.setOwner(entityMapper.map(identityManager.getUser(), UserSelect.class));

        JsonObject name = form.find("name", JsonObject.class);
        linkTo(methodOn(AddressSearchResource.class).list(null))
                .build(name::addLink);

        linkTo(methodOn(AddressesResource.class).save(new AddressesForm()))
                .build(form::addLink);

        return form;
    }

    @Override
    public AddressesForm read(UUID id) {
        Addresses entity = entityManager.find(Addresses.class, id);

        AddressesForm form = entityMapper.map(entity, AddressesForm.class);

        if (entity.getOwner().equals(identityManager.getUser())) {
            linkTo(methodOn(AddressesResource.class).update(entity.getId(), new AddressesForm()))
                    .build(form::addLink);
            linkTo(methodOn(AddressesResource.class).delete(entity.getId()))
                    .build(form::addLink);
        }

        JsonObject name = form.find("name", JsonObject.class);
        linkTo(methodOn(AddressSearchResource.class).list(null))
                .build(name::addLink);

        return form;
    }

    @Override
    public ResponseOk save(AddressesForm form) {
        extractAddressData(form);

        Addresses entity = restMapper.map(form, Addresses.class);

        entityManager.persist(entity);

        ResponseOk response = new ResponseOk();

        linkTo(methodOn(UserResource.class).read(entity.getOwner().getId()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }

    @Override
    public ResponseOk update(UUID id, AddressesForm form) {
        extractAddressData(form);

        Addresses entity = restMapper.map(form, Addresses.class);

        ResponseOk response = new ResponseOk();

        linkTo(methodOn(UserResource.class).read(entity.getOwner().getId()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }

    @Override
    public ResponseOk delete(UUID id) {
        Addresses entity = entityManager.getReference(Addresses.class, id);
        entityManager.remove(entity);
        ResponseOk response = new ResponseOk();

        linkTo(methodOn(UserResource.class).read(entity.getOwner().getId()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }

    private static void extractAddressData(AddressesForm form) {
        for (AddressForm item : form.getItems()) {
            MapBoxAddress row = item.getName();
            String[] segments = row.getName().split(",");
            item.setX(row.getPoint().getX());
            item.setY(row.getPoint().getY());
            if (segments.length == 3) {
                item.setStreet(segments[0].trim());

                String[] zipCodeState = segments[1].trim().split(" ");
                item.setZipCode(zipCodeState[0].trim());
                item.setState(zipCodeState[1].trim());

                item.setCountry(segments[2].trim());
            } else {
                item.setStreet(segments[0].trim());

                String[] zipCodeState = segments[2].split(" ");
                item.setZipCode(zipCodeState[0].trim());
                item.setState(zipCodeState[1].trim());

                item.setCountry(segments[3].trim());
            }
        }
    }

}
