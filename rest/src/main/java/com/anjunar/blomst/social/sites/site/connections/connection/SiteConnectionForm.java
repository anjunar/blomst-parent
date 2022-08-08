package com.anjunar.blomst.social.sites.site.connections.connection;

import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.AbstractRestEntityConverter;
import com.anjunar.common.rest.api.json.annotations.JsonSchema;
import com.anjunar.common.rest.api.json.schema.JsonNode;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.blomst.social.sites.Site;
import com.anjunar.blomst.social.sites.SiteConnection;
import com.anjunar.blomst.social.sites.site.SiteForm;

import jakarta.persistence.EntityManager;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class SiteConnectionForm extends AbstractRestEntity {

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "From")
    private UserSelect from;

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "To")
    private SiteForm to;

    public UserSelect getFrom() {
        return from;
    }

    public void setFrom(UserSelect from) {
        this.from = from;
    }

    public SiteForm getTo() {
        return to;
    }

    public void setTo(SiteForm to) {
        this.to = to;
    }

    public static class SiteConnectionFormConverter extends AbstractRestEntityConverter<SiteConnection, SiteConnectionForm> {

        public static final SiteConnectionFormConverter INSTANCE = new SiteConnectionFormConverter();

        @Override
        public SiteConnection updater(SiteConnectionForm restEntity, SiteConnection entity, EntityManager entityManager, IdentityProvider identityProvider) {
            entity.setFrom(identityProvider.getUser());
            entity.setTo(entityManager.find(Site.class, restEntity.getTo().getId()));
            return entity;
        }

        @Override
        public SiteConnectionForm factory(SiteConnectionForm restEntity, SiteConnection entity) {
            SiteConnectionForm form = super.factory(restEntity, entity);
            form.setFrom(UserSelect.factory(entity.getFrom()));
            form.setTo(SiteForm.factory(entity.getTo()));
            return form;
        }
    }

    public static SiteConnectionForm factory(SiteConnection entity) {
        return SiteConnectionForm.SiteConnectionFormConverter.INSTANCE.factory(new SiteConnectionForm(), entity);
    }

    public static SiteConnection updater(SiteConnectionForm form, SiteConnection entity, IdentityProvider identityProvider, EntityManager entityManager) {
        return SiteConnectionForm.SiteConnectionFormConverter.INSTANCE.updater(form, entity, entityManager, identityProvider);
    }


}
