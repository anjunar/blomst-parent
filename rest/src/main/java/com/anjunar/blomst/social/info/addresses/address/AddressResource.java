package com.anjunar.blomst.social.info.addresses.address;

import com.anjunar.blomst.control.users.Address;
import com.anjunar.blomst.control.users.AddressRight;
import com.anjunar.blomst.control.users.user.connections.categories.CategoriesResource;
import com.anjunar.blomst.control.users.user.connections.categories.CategoriesSearch;
import com.anjunar.blomst.social.info.addresses.AddressSearchResource;
import com.anjunar.blomst.social.info.addresses.AddressesResource;
import com.anjunar.blomst.social.info.addresses.AddressesSearch;
import com.anjunar.blomst.social.info.addresses.MapBoxAddress;
import com.anjunar.common.rest.api.ResponseOk;
import com.anjunar.common.rest.api.SecuredForm;
import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import com.anjunar.common.rest.mapper.ResourceRestMapper;
import com.anjunar.common.rest.schema.schema.JsonObject;
import com.anjunar.common.security.Category;
import com.anjunar.common.security.IdentityManager;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.*;

import java.util.UUID;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.*;

@ApplicationScoped
@Path("social/info/addresses/address")
public class AddressResource {

    private final ResourceEntityMapper entityMapper;

    private final ResourceRestMapper restMapper;

    private final EntityManager entityManager;

    private final IdentityManager identityManager;

    @Inject
    public AddressResource(ResourceEntityMapper entityMapper, ResourceRestMapper restMapper, EntityManager entityManager, IdentityManager identityManager) {
        this.entityMapper = entityMapper;
        this.restMapper = restMapper;
        this.entityManager = entityManager;
        this.identityManager = identityManager;
    }

    public AddressResource() {
        this(null, null, null, null);
    }

    @Path("create")
    @GET
    @Produces("application/json")
    @RolesAllowed({"Administrator", "User"})
    public SecuredForm<AddressSelect> create() {
        AddressSelect form = new AddressSelect();
        SecuredForm<AddressSelect> securedForm = new SecuredForm<>(form) {};

        JsonObject name = securedForm.find("name", JsonObject.class);
        linkTo(methodOn(AddressSearchResource.class).list(null))
                .build(name::addLink);

        linkTo(methodOn(AddressResource.class).save(new SecuredForm<>()))
                .build(securedForm::addLink);

        JsonObject visibility = securedForm.find("form", JsonObject.class);
        linkTo(methodOn(CategoriesResource.class).list(new CategoriesSearch()))
                .build(visibility::addLink);

        return securedForm;
    }

    @Produces("application/json")
    @GET
    @RolesAllowed({"Administrator", "User"})
    public SecuredForm<AddressForm> read(@QueryParam("id") UUID id) {
        Address entity = entityManager.find(Address.class, id);

        SecuredForm<AddressForm> securedForm = entityMapper.map(entity, new SecuredForm<>() {});

        if (entity.getOwner().equals(identityManager.getUser())) {
            linkTo(methodOn(AddressResource.class).update(entity.getId(), new SecuredForm<>()))
                    .build(securedForm::addLink);
            linkTo(methodOn(AddressResource.class).delete(entity.getId()))
                    .build(securedForm::addLink);
        }

        JsonObject visibility = securedForm.find("form", JsonObject.class);
        linkTo(methodOn(CategoriesResource.class).list(new CategoriesSearch()))
                .build(visibility::addLink);

        return securedForm;
    }

    @Consumes("application/json")
    @Produces("application/json")
    @POST
    @RolesAllowed({"Administrator", "User"})
    public ResponseOk save(SecuredForm<AddressSelect> securedForm) {
        AddressForm addressForm = extractAddressData(securedForm.getForm());

        Address entity = restMapper.map(addressForm, Address.class, securedForm.getSchema(), false, false);
        entity.setOwner(identityManager.getUser());

        entityManager.persist(entity);

        AddressRight right = new AddressRight();
        right.setSource(entity);
        right.getCategories().addAll(securedForm.find("form", JsonObject.class)
                .getVisibility()
                .stream()
                .map(categoryType -> entityManager.find(Category.class, categoryType.getId()))
                .toList()
        );

        entityManager.persist(right);

        ResponseOk response = new ResponseOk();

        linkTo(methodOn(AddressesResource.class).list(new AddressesSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }

    @Consumes("application/json")
    @Produces("application/json")
    @PUT
    @RolesAllowed({"Administrator", "User"})
    public ResponseOk update(@QueryParam("id") UUID id, SecuredForm<AddressForm> securedForm) {
        Address entity = restMapper.map(securedForm, Address.class);
        entity.setOwner(identityManager.getUser());

        ResponseOk response = new ResponseOk();

        linkTo(methodOn(AddressesResource.class).list(new AddressesSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }

    @DELETE
    @Produces("application/json")
    @RolesAllowed({"Administrator", "User"})
    public ResponseOk delete(UUID id) {
        Address entity = entityManager.getReference(Address.class, id);
        entityManager.remove(entity);
        ResponseOk response = new ResponseOk();

        linkTo(methodOn(AddressesResource.class).list(new AddressesSearch()))
                .withRel("redirect")
                .build(response::addLink);

        return response;
    }

    private static AddressForm extractAddressData(AddressSelect item) {
        AddressForm addressForm = new AddressForm();
        MapBoxAddress row = item.getName();
        String[] segments = row.getName().split(",");
        addressForm.setX(row.getPoint().getX());
        addressForm.setY(row.getPoint().getY());
        if (segments.length == 3) {
            addressForm.setStreet(segments[0].trim());

            String[] zipCodeState = segments[1].trim().split(" ");
            addressForm.setZipCode(zipCodeState[0].trim());
            addressForm.setState(zipCodeState[1].trim());

            addressForm.setCountry(segments[2].trim());
        } else {
            addressForm.setStreet(segments[0].trim());

            String[] zipCodeState = segments[2].split(" ");
            addressForm.setZipCode(zipCodeState[0].trim());
            addressForm.setState(zipCodeState[1].trim());

            addressForm.setCountry(segments[3].trim());
        }
        return addressForm;
    }

}
