package com.anjunar.blomst.social.sites.site;

import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.ImageType;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;

import java.net.URL;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class SiteForm extends AbstractRestEntity {

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Name", naming = true)
    private String name;

    @JsonSchema(widget = JsonNode.Widget.URL, title = "Homepage")
    private URL homepage;

    @JsonSchema(widget = JsonNode.Widget.IMAGE, title = "Image")
    private ImageType image;

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

    public ImageType getImage() {
        return image;
    }

    public void setImage(ImageType image) {
        this.image = image;
    }

}
