package com.anjunar.blomst.social.sites.site.connections.connection;

import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.blomst.social.sites.site.SiteForm;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.mapper.annotations.MapperWrite;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class SiteConnectionForm extends AbstractRestEntity {

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "From")
    @MapperWrite
    private UserSelect from;

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "To")
    @MapperWrite
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

}
