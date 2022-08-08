package com.anjunar.blomst.social.sites.site;

import com.anjunar.common.filedisk.Image;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.AbstractRestEntityConverter;
import com.anjunar.common.rest.api.ImageType;
import com.anjunar.common.rest.api.json.annotations.JsonSchema;
import com.anjunar.common.rest.api.json.schema.JsonNode;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.blomst.social.sites.Site;

import jakarta.persistence.EntityManager;
import java.net.URL;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class SiteForm extends AbstractRestEntity {

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Name", naming = true)
    private String name;

    @JsonSchema(widget = JsonNode.Widget.URL, title = "Homepage")
    private URL homepage;

    @JsonSchema(widget = JsonNode.Widget.IMAGE, title = "Image")
    private ImageType image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public URL getHomepage() {
        return homepage;
    }

    public void setHomepage(URL homepage) {
        this.homepage = homepage;
    }

    public ImageType getImage() {
        return image;
    }

    public void setImage(ImageType image) {
        this.image = image;
    }

    public static class SiteFormConverter extends AbstractRestEntityConverter<Site, SiteForm> {

        public static final SiteFormConverter INSTANCE = new SiteFormConverter();

        @Override
        public Site updater(SiteForm restEntity, Site entity, EntityManager entityManager, IdentityProvider identityProvider) {
            entity.setHomepage(restEntity.getHomepage());
            entity.setName(restEntity.getName());
            entity.setPicture(ImageType.updater(restEntity.getImage(), entity.getPicture() == null ? new Image() : entity.getPicture()));
            return entity;
        }

        @Override
        public SiteForm factory(SiteForm restEntity, Site entity) {
            SiteForm form = super.factory(restEntity, entity);
            form.setHomepage(entity.getHomepage());
            form.setImage(ImageType.factory(entity.getPicture()));
            form.setName(entity.getName());
            return form;
        }
    }

    public static SiteForm factory(Site entity) {
        return SiteForm.SiteFormConverter.INSTANCE.factory(new SiteForm(), entity);
    }

    public static Site updater(SiteForm form, Site entity, IdentityProvider identityProvider, EntityManager entityManager) {
        return SiteForm.SiteFormConverter.INSTANCE.updater(form, entity, entityManager, identityProvider);
    }

}
