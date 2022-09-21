package com.anjunar.blomst.social.timeline.post;

import com.anjunar.blomst.social.timeline.ImagePost;
import com.anjunar.blomst.social.timeline.LinkPost;
import com.anjunar.common.rest.mapper.annotations.MapperPolymorphism;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.anjunar.common.rest.api.ImageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.net.URL;

@MapperPolymorphism(LinkPost.class)
public class LinkPostForm extends AbstractPostForm {

    private final static Logger log = LoggerFactory.getLogger(LinkPostForm.class);

    @JsonSchema(widget = JsonNode.Widget.URL, title = "Link")
    private URL link;

    @NotBlank
    @Size(min = 3, max = 80)
    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Title")
    private String title;

    @Size(max = 255)
    @JsonSchema(widget = JsonNode.Widget.TEL, title = "Description")
    private String description;

    @JsonSchema(widget = JsonNode.Widget.IMAGE, title = "Picture")
    private ImageType image;

    @Override
    public <E> E accept(AbstractPostFormVisitor<E> visitor) {
        return visitor.visit(this);
    }

    public URL getLink() {
        return link;
    }

    public void setLink(URL link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ImageType getImage() {
        return image;
    }

    public void setImage(ImageType image) {
        this.image = image;
    }

}
