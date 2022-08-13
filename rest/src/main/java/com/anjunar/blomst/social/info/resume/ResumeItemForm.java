package com.anjunar.blomst.social.info.resume;

import com.anjunar.blomst.social.sites.site.SiteForm;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.AbstractRestEntityConverter;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.blomst.control.users.ResumeItem;
import com.anjunar.blomst.social.sites.Site;

import jakarta.persistence.EntityManager;
import java.time.Year;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class ResumeItemForm extends AbstractRestEntity {

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Site")
    private SiteForm site;

    @JsonSchema(widget = JsonNode.Widget.NUMBER, title = "Start")
    private Year start;

    @JsonSchema(widget = JsonNode.Widget.NUMBER, title = "End")
    private Year end;

    public SiteForm getSite() {
        return site;
    }

    public void setSite(SiteForm site) {
        this.site = site;
    }

    public Year getStart() {
        return start;
    }

    public void setStart(Year start) {
        this.start = start;
    }

    public Year getEnd() {
        return end;
    }

    public void setEnd(Year end) {
        this.end = end;
    }

    public static class ResumeItemConverter extends AbstractRestEntityConverter<ResumeItem, ResumeItemForm> {

        public static final ResumeItemConverter INSTANCE = new ResumeItemConverter();

        @Override
        public ResumeItem updater(ResumeItemForm restEntity, ResumeItem entity, EntityManager entityManager, IdentityProvider identityProvider) {
            entity.setSite(entityManager.find(Site.class, restEntity.getSite().getId()));
            entity.setEnd(restEntity.getEnd());
            entity.setStart(restEntity.getStart());
            return entity;
        }

        @Override
        public ResumeItemForm factory(ResumeItemForm restEntity, ResumeItem entity) {
            ResumeItemForm form = super.factory(restEntity, entity);
            form.setSite(SiteForm.factory(entity.getSite()));
            form.setEnd(entity.getEnd());
            form.setStart(entity.getStart());
            return form;
        }

    }

    public static ResumeItemForm factory(ResumeItem entity) {
        return ResumeItemConverter.INSTANCE.factory(new ResumeItemForm(), entity);
    }

    public static ResumeItem updater(ResumeItemForm form, ResumeItem entity, IdentityProvider identityProvider, EntityManager entityManager) {
        return ResumeItemConverter.INSTANCE.updater(form, entity, entityManager, identityProvider);
    }

}
