package com.anjunar.blomst.social.timeline.post;

import com.anjunar.common.filedisk.Image;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.anjunar.blomst.social.timeline.LinkPost;
import com.anjunar.common.rest.api.ImageType;
import com.anjunar.common.security.IdentityProvider;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.Optional;

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
