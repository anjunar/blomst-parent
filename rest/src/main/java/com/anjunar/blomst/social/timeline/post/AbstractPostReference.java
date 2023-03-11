package com.anjunar.blomst.social.timeline.post;

import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.UUIDReference;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ImagePostReference.class, name = "ImagePost"),
        @JsonSubTypes.Type(value = VideoPostReference.class, name = "VideoPost"),
        @JsonSubTypes.Type(value = TextPostReference.class, name = "TextPost")}
)
public class AbstractPostReference extends AbstractRestEntity implements UUIDReference { }
