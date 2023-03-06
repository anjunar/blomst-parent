package com.anjunar.blomst.social.info.resume.event;

import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.blomst.social.sites.site.SiteForm;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.time.Year;

@JsonSchema(widget = JsonNode.Widget.FORM)
@JsonTypeName("Resume")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, visible = true)
public class EventForm extends AbstractRestEntity {

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Owner", readOnly = true)
    private UserSelect owner;

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Site")
    private SiteForm site;

    @JsonSchema(widget = JsonNode.Widget.NUMBER, title = "Start")
    private Year start;

    @JsonSchema(widget = JsonNode.Widget.NUMBER, title = "End")
    private Year end;

    public UserSelect getOwner() {
        return owner;
    }

    public void setOwner(UserSelect owner) {
        this.owner = owner;
    }

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
}
