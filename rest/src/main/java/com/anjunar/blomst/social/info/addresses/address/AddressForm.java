package com.anjunar.blomst.social.info.addresses.address;

import com.anjunar.blomst.shared.users.user.UserSelect;
import com.anjunar.blomst.social.info.addresses.MapBoxAddress;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.AbstractSchemaEntity;
import com.anjunar.common.rest.mapper.annotations.MapperPermanent;
import com.anjunar.common.rest.schema.CategoryType;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;

import java.util.Set;

@JsonSchema(widget = JsonNode.Widget.FORM)
public class AddressForm extends AbstractRestEntity {

    @JsonSchema(widget = JsonNode.Widget.LAZY_SELECT, title = "Owner", readOnly = true)
    @MapperPermanent
    private UserSelect owner;

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Street", readOnly = true)
    @MapperPermanent
    private String street;

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Zip Code", readOnly = true)
    @MapperPermanent
    private String zipCode;

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "State", readOnly = true)
    @MapperPermanent
    private String state;

    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Country", readOnly = true)
    @MapperPermanent
    private String country;

    @JsonSchema(widget = JsonNode.Widget.NUMBER, title = "X", readOnly = true)
    @MapperPermanent
    private Float x;

    @JsonSchema(widget = JsonNode.Widget.NUMBER, title = "Y", readOnly = true)
    @MapperPermanent
    private Float y;

    public UserSelect getOwner() {
        return owner;
    }

    public void setOwner(UserSelect owner) {
        this.owner = owner;
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
