package com.anjunar.blomst.social.timeline.post.comments.comment;

import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.UUIDReference;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("Comment")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, visible = true)
public class CommentReference extends AbstractRestEntity implements UUIDReference { }
