package com.anjunar.blomst.social.timeline.post;

import com.anjunar.blomst.social.timeline.ImagePost;
import com.anjunar.common.rest.mapper.annotations.MapperPolymorphism;

@MapperPolymorphism(ImagePost.class)
public class ImagePostReference extends AbstractPostReference{ }
