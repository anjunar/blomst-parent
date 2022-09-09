package com.anjunar.blomst.social.communities.community.connections.connection;

import com.anjunar.blomst.control.roles.role.RoleForm;
import com.anjunar.blomst.control.users.user.UserForm;
import com.anjunar.blomst.control.users.user.UserSelect;
import com.anjunar.blomst.social.communities.Status;
import com.anjunar.blomst.social.communities.community.CommunityForm;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.mapper.annotations.MapperView;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class CommunityConnectionForm extends AbstractRestEntity {

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "From", readOnly = true)
    @MapperView(UserSelect.class)
    private UserForm from;

    @JsonSchema(widget = JsonNode.Widget.SELECT, title = "Status")
    private Status status;

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Role")
    private RoleForm role;

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "To", readOnly = true)
    private CommunityForm to;

    public UserForm getFrom() {
        return from;
    }

    public void setFrom(UserForm from) {
        this.from = from;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public RoleForm getRole() {
        return role;
    }

    public void setRole(RoleForm role) {
        this.role = role;
    }

    public CommunityForm getTo() {
        return to;
    }

    public void setTo(CommunityForm to) {
        this.to = to;
    }

}
