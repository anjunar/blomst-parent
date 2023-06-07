package com.anjunar.blomst.social.info.addresses.address;

import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonSchema(widget = JsonNode.Widget.FORM)
@JsonTypeName("Address")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, visible = true)
public class AddressForm extends AbstractRestEntity {

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Owner", readOnly = true)
    @JsonProperty(required = true)
    private UserSelect owner;

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Description", readOnly = true)
    @JsonProperty(required = true)
    private String description;

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Street", readOnly = true)
    @JsonProperty(required = true)
    private String street;

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Zip Code", readOnly = true)
    @JsonProperty(required = true)
    private String zipCode;

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "State", readOnly = true)
    @JsonProperty(required = true)
    private String state;

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Country", readOnly = true)
    @JsonProperty(required = true)
    private String country;

    @JsonSchema(widget = JsonNode.Widget.NUMBER, title = "X", readOnly = true)
    @JsonProperty(required = true)
    private Float x;

    @JsonSchema(widget = JsonNode.Widget.NUMBER, title = "Y", readOnly = true)
    @JsonProperty(required = true)
    private Float y;

    public UserSelect getOwner() {
        return owner;
    }

    public void setOwner(UserSelect owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public Float getY() {
        return y;
    }

    public void setY(Float y) {
        this.y = y;
    }
}
