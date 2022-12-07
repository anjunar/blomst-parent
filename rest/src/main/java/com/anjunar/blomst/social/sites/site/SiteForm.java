package com.anjunar.blomst.social.sites.site;

import com.anjunar.blomst.control.users.user.ImageConverter;
import com.anjunar.blomst.shared.MapBoxConverter;
import com.anjunar.blomst.social.info.addresses.MapBoxAddress;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.ImageType;
import com.anjunar.common.rest.mapper.annotations.MapperConverter;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.net.URL;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class SiteForm extends AbstractRestEntity {

    @Size(min = 3, max = 80)
    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Name", naming = true)
    private String name;

    @JsonSchema(widget = JsonNode.Widget.URL, title = "Homepage")
    private URL homepage;

    @Pattern(regexp = "[\\d\\s]+")
    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Phone")
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

    public URL getHomepage() {
        return homepage;
    }

    public void setHomepage(URL homepage) {
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
