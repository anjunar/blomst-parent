package com.anjunar.blomst.social.info.resume;

import com.anjunar.blomst.social.sites.site.SiteForm;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;

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

}
