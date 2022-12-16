package com.anjunar.blomst.shared.site;

import com.anjunar.blomst.control.users.user.ImageConverter;
import com.anjunar.blomst.shared.mapbox.MapBoxConverter;
import com.anjunar.blomst.social.info.addresses.MapBoxAddress;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.ImageType;
import com.anjunar.common.rest.mapper.annotations.MapperConverter;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;

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
    @MapperConverter(ImageConverter.class)
    private ImageType picture;

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

    public ImageType getPicture() {
        return picture;
    }

    public void setPicture(ImageType picture) {
        this.picture = picture;
    }
}
