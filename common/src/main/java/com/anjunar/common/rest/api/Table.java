package com.anjunar.common.rest.api;

import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonArray;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.anjunar.common.rest.schema.schema.JsonObject;
import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author by Patrick Bittner on 12.06.15.
 */
@JsonSchema(widget = JsonNode.Widget.TABLE)
public class Table<R> extends AbstractSchemaEntity{

    private final List<R> rows;

    @JsonSchema(widget = JsonNode.Widget.NUMBER, title = "Size")
    private final long size;

    public Table(List<R> rows, long size) {
        this.rows = rows;
        this.size = size;
    }

    public Table() {
        this(null, 0);
    }

    public List<R> getRows() {
        return rows;
    }

    public long getSize() {
        return size;
    }

    public void visible(String... values) {
        Set<String> properties = Sets.newHashSet(values);
        JsonArray jsonArray = find("rows", JsonArray.class);
        JsonObject items = (JsonObject) jsonArray.getItems();
        for (Map.Entry<String, JsonNode> entry : items.getProperties().entrySet()) {
            entry.getValue().setVisible(properties.contains(entry.getKey()));
        }
    }
}
