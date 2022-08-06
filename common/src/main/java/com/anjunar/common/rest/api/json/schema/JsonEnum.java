package com.anjunar.common.rest.api.json.schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.ArrayList;
import java.util.List;

@JsonTypeName("enum")
public class JsonEnum extends JsonNode {

    @JsonProperty("enum")
    private List<String> enums = new ArrayList<>();

    public List<String> getEnums() {
        return enums;
    }

    public void setEnums(List<String> enums) {
        this.enums = enums;
    }
}
