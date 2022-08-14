package com.anjunar.blomst.control.mail;

import com.anjunar.blomst.control.mail.template.TemplateForm;
import com.anjunar.blomst.control.mail.template.TemplateResource;
import com.anjunar.blomst.control.notifications.Notification;
import com.anjunar.blomst.social.sites.SiteConnection;
import com.anjunar.common.mail.Template;
import com.anjunar.common.rest.link.LinkDescription;
import com.anjunar.common.rest.api.Table;
import com.anjunar.common.rest.api.ListResourceTemplate;

import com.anjunar.common.rest.objectmapper.NewInstanceProvider;
import com.anjunar.common.rest.objectmapper.ObjectMapper;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

import static com.anjunar.common.rest.link.WebURLBuilderFactory.*;

@Path("mail/templates")
@ApplicationScoped
public class TemplatesResource implements ListResourceTemplate<TemplateForm, TemplatesSearch> {

    private final TemplatesService service;

    @Inject
    public TemplatesResource(TemplatesService service) {
        this.service = service;
    }

    public TemplatesResource() {
        this(null);
    }

    @Override
    @Transactional
    @RolesAllowed("Administrator")
    @LinkDescription("Table Template")
    public Table<TemplateForm> list(TemplatesSearch search) {

        List<Template> templates = service.find(search);
        long count = service.count(search);

        List<TemplateForm> resources = new ArrayList<>();

        for (Template template : templates) {

            ObjectMapper mapper = new ObjectMapper();
            TemplateForm resource = mapper.map(template, TemplateForm.class);

            linkTo(methodOn(TemplateResource.class).read(template.getId()))
                    .build(resource::addLink);

            resources.add(resource);

        }

        Table<TemplateForm> table = new Table<>(resources, count) {};

        linkTo(methodOn(TemplateResource.class).create())
                .build(table::addLink);

        return table;
    }
}
