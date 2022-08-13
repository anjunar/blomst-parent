package com.anjunar.blomst.social.info.resume;

import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.AbstractRestEntityConverter;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.blomst.control.users.Resume;
import com.anjunar.blomst.control.users.ResumeItem;
import com.anjunar.blomst.shared.users.user.UserSelect;

import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class ResumeForm extends AbstractRestEntity {

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Owner", readOnly = true)
    private UserSelect owner;

    @JsonSchema(widget = JsonNode.Widget.REPEAT, title = "Items")
    private final List<ResumeItemForm> items = new ArrayList<>();

    public UserSelect getOwner() {
        return owner;
    }

    public void setOwner(UserSelect owner) {
        this.owner = owner;
    }

    public List<ResumeItemForm> getItems() {
        return items;
    }

    public static class ResumeFormConverter extends AbstractRestEntityConverter<Resume, ResumeForm> {

        public static final ResumeFormConverter INSTANCE = new ResumeFormConverter();

        @Override
        public Resume updater(ResumeForm restEntity, Resume entity, EntityManager entityManager, IdentityProvider identityProvider) {
            entity.setOwner(identityProvider.getUser());
            entity.getItems().clear();
            for (ResumeItemForm item : restEntity.getItems()) {
                entity.getItems().add(ResumeItemForm.updater(item, new ResumeItem(), identityProvider, entityManager));
            }
            return entity;
        }

        @Override
        public ResumeForm factory(ResumeForm restEntity, Resume entity) {
            ResumeForm form = super.factory(restEntity, entity);

            form.setOwner(UserSelect.factory(entity.getOwner()));
            for (ResumeItem item : entity.getItems()) {
                form.getItems().add(ResumeItemForm.factory(item));
            }

            return form;
        }
    }

    public static ResumeForm factory(Resume entity) {
        return ResumeForm.ResumeFormConverter.INSTANCE.factory(new ResumeForm(), entity);
    }

    public static Resume updater(ResumeForm form, Resume entity, IdentityProvider identityProvider, EntityManager entityManager) {
        return ResumeForm.ResumeFormConverter.INSTANCE.updater(form, entity, entityManager, identityProvider);
    }


}
