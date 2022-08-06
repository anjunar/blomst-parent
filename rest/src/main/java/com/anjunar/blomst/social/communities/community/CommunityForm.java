package com.anjunar.blomst.social.communities.community;

import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.AbstractRestEntityConverter;
import com.anjunar.common.rest.api.json.annotations.JsonSchema;
import com.anjunar.common.rest.api.json.schema.JsonNode;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.blomst.social.communities.Community;

import javax.persistence.EntityManager;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class CommunityForm extends AbstractRestEntity {

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Name", naming = true)
    private String name;

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Description")
    private String description;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static class CommunityFormConverter extends AbstractRestEntityConverter<Community, CommunityForm> {

        public static final CommunityFormConverter INSTANCE = new CommunityFormConverter();

        @Override
        public Community updater(CommunityForm restEntity, Community entity, EntityManager entityManager, IdentityProvider identityProvider) {
            entity.setName(restEntity.getName());
            entity.setDescription(restEntity.getDescription());
            return entity;
        }

        @Override
        public CommunityForm factory(CommunityForm restEntity, Community entity) {
            CommunityForm form = super.factory(restEntity, entity);
            form.setName(entity.getName());
            form.setDescription(entity.getDescription());
            return form;
        }
    }

    public static CommunityForm factory(Community entity) {
        return CommunityFormConverter.INSTANCE.factory(new CommunityForm(), entity);
    }

    public static Community updater(CommunityForm resource, Community entity, IdentityProvider identityProvider, EntityManager entityManager) {
        return CommunityFormConverter.INSTANCE.updater(resource, entity, entityManager, identityProvider);
    }

}
