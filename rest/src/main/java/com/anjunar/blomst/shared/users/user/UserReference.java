package com.anjunar.blomst.shared.users.user;

import com.anjunar.common.rest.api.NamedReference;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.google.common.base.Strings;

public class UserReference extends IdentityReference implements NamedReference {

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Nick Name", readOnly = true)
    private String nickName;

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "First Name", readOnly = true)
    private String firstName;

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Last Name", readOnly = true)
    private String lastName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Name", readOnly = true)
    public String getName() {
        if (!Strings.isNullOrEmpty(firstName) && !Strings.isNullOrEmpty(lastName)) {
            return firstName + " " + lastName;
        }
        if (!Strings.isNullOrEmpty(nickName)) {
            return nickName;
        }
        return "Anonymous";
    }
}
