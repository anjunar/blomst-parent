package com.anjunar.blomst.social.chat.messages;

import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonSchema(widget = JsonNode.Widget.FORM)
@JsonTypeName("ChatMessage")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, visible = true)
public class ChatMessageRow extends AbstractRestEntity {

    @JsonSchema(widget = JsonNode.Widget.TEXTAREA, title = "Message")
    @JsonProperty(required = true)
    private String text;

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Owner")
    @JsonProperty(required = true)
    private UserSelect owner;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserSelect getOwner() {
        return owner;
    }

    public void setOwner(UserSelect owner) {
        this.owner = owner;
    }
}
