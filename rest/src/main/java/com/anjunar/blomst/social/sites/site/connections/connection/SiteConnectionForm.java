package com.anjunar.blomst.social.sites.site.connections.connection;

import com.anjunar.blomst.shared.site.SiteSelect;
import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.blomst.social.sites.site.SiteForm;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class SiteConnectionForm extends AbstractRestEntity {

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "From")
    private UserSelect from;

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "To")
    private SiteSelect to;

    public UserSelect getFrom() {
        return from;
    }

    public void setFrom(UserSelect from) {
        this.from = from;
    }

    public SiteSelect getTo() {
        return to;
    }

    public void setTo(SiteSelect to) {
        this.to = to;
    }

}
