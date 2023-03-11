package com.anjunar.blomst.social.timeline.post;

import com.anjunar.blomst.social.timeline.VideoPost;
import com.anjunar.common.rest.mapper.annotations.MapperPolymorphism;

@MapperPolymorphism(VideoPost.class)
public class VideoPostReference extends AbstractPostReference {
}
