package com.anjunar.blomst.social.sites.site;

import com.anjunar.blomst.control.users.user.ImageConverter;
import com.anjunar.blomst.shared.Alternative;
import com.anjunar.blomst.shared.alternatives.alternative.AlternativeForm;
import com.anjunar.blomst.shared.mapbox.MapBoxConverter;
import com.anjunar.blomst.social.info.addresses.MapBoxAddress;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.ImageType;
import com.anjunar.common.rest.mapper.annotations.MapperConverter;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.net.URL;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class SiteForm extends AbstractRestEntity {

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT_NAME, title = "Name", naming = true)
    private AlternativeForm name;

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT_NAME, title = "Homepage")
    private AlternativeForm homepage;

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT_NAME, title = "Phone")
    private AlternativeForm phone;

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Address")
    @MapperConverter(MapBoxConverter.class)
    private MapBoxAddress address;

    @JsonSchema(widget = JsonNode.Widget.IMAGE, title = "Image")
    @MapperConverter(ImageConverter.class)
    private ImageType picture;

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

    public ImageType getPicture() {
        return picture;
    }

    public void setPicture(ImageType picture) {
        this.picture = picture;
    }

}
