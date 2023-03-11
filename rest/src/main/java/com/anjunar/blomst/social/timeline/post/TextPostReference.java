package com.anjunar.blomst.social.timeline.post;

import com.anjunar.blomst.social.timeline.TextPost;
import com.anjunar.common.rest.mapper.annotations.MapperPolymorphism;

@MapperPolymorphism(TextPost.class)
public class TextPostReference extends AbstractPostReference {
}
