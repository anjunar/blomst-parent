package com.anjunar.blomst.social.pages.page;

import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.UUIDReference;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("Page")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, visible = true)
public class PageReference extends AbstractRestEntity implements UUIDReference { }
