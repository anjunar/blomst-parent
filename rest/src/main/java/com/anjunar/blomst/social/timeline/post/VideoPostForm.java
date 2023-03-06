package com.anjunar.blomst.social.timeline.post;

import com.anjunar.blomst.control.users.user.ImageConverter;
import com.anjunar.blomst.social.timeline.VideoPost;
import com.anjunar.common.rest.api.MediaType;
import com.anjunar.common.rest.mapper.annotations.MapperConverter;
import com.anjunar.common.rest.mapper.annotations.MapperPolymorphism;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@MapperPolymorphism(VideoPost.class)
public class VideoPostForm extends AbstractPostForm {

    private final static Logger log = LoggerFactory.getLogger(VideoPostForm.class);

    @MapperConverter(ImageConverter.class)
    private MediaType video = new MediaType();

    @Override
    public <E> E accept(AbstractPostFormVisitor<E> visitor) {
        return visitor.visit(this);
    }

    public MediaType getVideo() {
        return video;
    }

    public void setVideo(MediaType video) {
        this.video = video;
    }
}
