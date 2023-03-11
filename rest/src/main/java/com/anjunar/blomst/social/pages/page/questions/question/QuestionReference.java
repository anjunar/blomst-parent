package com.anjunar.blomst.social.pages.page.questions.question;

import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.UUIDReference;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("Question")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, visible = true)
public class QuestionReference extends AbstractRestEntity implements UUIDReference { }
