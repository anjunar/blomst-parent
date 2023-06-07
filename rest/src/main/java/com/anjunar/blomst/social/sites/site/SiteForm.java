package com.anjunar.blomst.social.sites.site;

import com.anjunar.common.filedisk.MediaURLConverter;
import com.anjunar.blomst.shared.alternatives.alternative.AlternativeForm;
import com.anjunar.blomst.shared.mapbox.MapBoxConverter;
import com.anjunar.blomst.social.info.addresses.MapBoxAddress;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.MediaType;
import com.anjunar.common.rest.mapper.annotations.MapperConverter;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonSchema(widget = JsonNode.Widget.FORM)
@JsonTypeName("Site")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, visible = true)
public class SiteForm extends AbstractRestEntity {

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT_NAME, title = "Name")
    private AlternativeForm name;

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT_NAME, title = "Homepage")
    private AlternativeForm homepage;

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT_NAME, title = "Phone")
    private AlternativeForm phone;

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Address")
    @MapperConverter(MapBoxConverter.class)
    private MapBoxAddress address;

    @JsonSchema(widget = JsonNode.Widget.IMAGE, title = "Image")
    @MapperConverter(MediaURLConverter.class)
    private MediaType picture;

    public AlternativeForm getName() {
        return name;
    }

    public void setName(AlternativeForm name) {
        this.name = name;
    }

    public AlternativeForm getHomepage() {
        return homepage;
    }

    public void setHomepage(AlternativeForm homepage) {
        this.homepage = homepage;
    }

    public AlternativeForm getPhone() {
        return phone;
    }

    public void setPhone(AlternativeForm phone) {
        this.phone = phone;
    }

    public MapBoxAddress getAddress() {
        return address;
    }

    public void setAddress(MapBoxAddress address) {
        this.address = address;
    }

    public MediaType getPicture() {
        return picture;
    }

    public void setPicture(MediaType picture) {
        this.picture = picture;
    }

}
