package com.anjunar.blomst.social.chat;

import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class ChatMessageForm extends AbstractRestEntity {

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Message")
    private String text;

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Owner")
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
