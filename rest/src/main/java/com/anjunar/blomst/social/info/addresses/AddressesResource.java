package com.anjunar.blomst.social.info.addresses;

import com.anjunar.blomst.control.users.Address;
import com.anjunar.blomst.social.info.addresses.address.AddressForm;
import com.anjunar.blomst.social.info.addresses.address.AddressResource;
import com.anjunar.common.rest.api.ListResourceTemplate;
import com.anjunar.common.rest.api.Table;
import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import com.anjunar.common.security.IdentityManager;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;

import java.util.ArrayList;
import java.util.List;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.*;

@ApplicationScoped
@Path("social/info/addresses")
public class AddressesResource implements ListResourceTemplate<AddressForm, AddressesSearch> {

    private final AddressesService service;

    private final ResourceEntityMapper entityMapper;

    private final IdentityManager identityManager;

    @Inject
    public AddressesResource(AddressesService service, ResourceEntityMapper entityMapper, IdentityManager identityManager) {
        this.service = service;
        this.entityMapper = entityMapper;
        this.identityManager = identityManager;
    }

    public AddressesResource() {
        this(null, null, null);
    }

    @Override
    @RolesAllowed({"Administrator", "User"})
    public Table<AddressForm> list(AddressesSearch search) {
        List<Address> addresses = service.find(search);
        long count = service.count(search);

        List<AddressForm> resources = new ArrayList<>();
        Table<AddressForm> table = new Table<>(resources, count) {};


        for (Address address : addresses) {
            AddressForm form = entityMapper.map(address, AddressForm.class, table);

            linkTo(methodOn(AddressResource.class).read(address.getId()))
                    .build(form::addLink);

            resources.add(form);
        }

        if (search.getOwner().equals(identityManager.getUser().getId())) {
            linkTo(methodOn(AddressResource.class).create())
                    .build(table::addLink);
        }

        return table;
    }
}
