package com.anjunar.common.rest.api;

import com.anjunar.common.rest.api.json.annotations.JsonSchema;
import com.anjunar.common.rest.api.json.schema.JsonNode;

import java.util.List;

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

}
