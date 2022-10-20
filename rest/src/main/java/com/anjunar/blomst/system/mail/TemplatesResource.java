package com.anjunar.blomst.system.mail;

import com.anjunar.blomst.system.mail.template.TemplateForm;
import com.anjunar.blomst.system.mail.template.TemplateResource;
import com.anjunar.common.mail.Template;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.Table;
import com.anjunar.common.rest.api.ListResourceTemplate;

import com.anjunar.common.rest.mapper.ResourceEntityMapper;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.*;

@Path("system/mail/templates")
@ApplicationScoped
public class TemplatesResource implements ListResourceTemplate<TemplateForm, TemplatesSearch> {

    private final TemplatesService service;

    private final ResourceEntityMapper mapper;

    @Inject
    public TemplatesResource(TemplatesService service, ResourceEntityMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    public TemplatesResource() {
        this(null, null);
    }

    @Override
    @RolesAllowed("Administrator")
    @LinkDescription("Table Template")
    public Table<TemplateForm> list(TemplatesSearch search) {

        List<Template> templates = service.find(search);
        long count = service.count(search);

        List<TemplateForm> resources = new ArrayList<>();
        Table<TemplateForm> table = new Table<>(resources, count) {};


        for (Template template : templates) {

            TemplateForm resource = mapper.map(template, TemplateForm.class, table);

            linkTo(methodOn(TemplateResource.class).read(template.getId()))
                    .build(resource::addLink);

            resources.add(resource);

        }

        linkTo(methodOn(TemplateResource.class).create())
                .build(table::addLink);

        return table;
    }
}
