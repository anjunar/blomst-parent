package com.anjunar.blomst.shared.site;

import com.anjunar.common.filedisk.MediaURLConverter;
import com.anjunar.blomst.shared.mapbox.MapBoxConverter;
import com.anjunar.blomst.social.info.addresses.MapBoxAddress;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.MediaType;
import com.anjunar.common.rest.mapper.annotations.MapperConverter;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("Site")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, visible = true)
public class SiteSelect extends AbstractRestEntity {

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Name", naming = true)
    @MapperConverter(StringConverter.class)
    private String name;

    @JsonSchema(widget = JsonNode.Widget.URL, title = "Homepage")
    @MapperConverter(StringConverter.class)
    private String homepage;

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Phone")
    @MapperConverter(StringConverter.class)
    private String phone;

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Address")
    @MapperConverter(MapBoxConverter.class)
    private MapBoxAddress address;

    @JsonSchema(widget = JsonNode.Widget.IMAGE, title = "Image")
    @MapperConverter(MediaURLConverter.class)
    private MediaType picture;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
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
