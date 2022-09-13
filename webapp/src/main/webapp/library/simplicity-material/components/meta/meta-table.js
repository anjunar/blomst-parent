import {customComponents} from "../../../simplicity-core/simplicity.js";
import MatTable from "../table/mat-table.js";
import {libraryLoader} from "../../../simplicity-core/processors/loader-processor.js";
import MetaColumn from "./meta-column.js";
import {debounce, isEqual, Membrane} from "../../../simplicity-core/services/tools.js";
import MatTableExtension from "../table/mat-table-extension.js";
import MatTableSearch from "../table/mat-table-search.js";
import MetaTableFilter from "./meta-table-filter.js";

class MetaTable extends HTMLElement {

    parent;

    schema = {
        links : {},
        properties: {
            rows : {
                items : {
                    properties : {}
                }
            }
        }
    };

    links(links) {
        return Object.values(links).filter((link) => link.method === "GET");
    }

    onRowClick(event) {
        this.dispatchEvent(new CustomEvent("model", {detail : event.detail}));
    }

    initialize() {
        MetaTable.prototype.search = debounce(() => {
            let table = this.querySelector("table");
            table.load();
        }, 300)
    }

/*
    initialize() {
        let table = this.querySelector("table");
        Membrane.track(this, {
            property : "schema",
            element : this,
            handler : () => {
                const tx = db.transaction("table", "readwrite");
                const store = tx.objectStore("table");
                let idbRequest = store.get(JSON.stringify(this.schema));
                idbRequest.onsuccess = (event) => {
                    if (event.target.result) {
                        table.columns = JSON.parse(event.target.result.columns);
                        table.load();
                    }
                }
            }
        })

        table.addEventListener("load", () => {
            const tx = db.transaction("table", "readwrite");
            const store = tx.objectStore("table");
            store.put({
                id: JSON.stringify(this.schema),
                columns: JSON.stringify(table.columns)
            });
        })
    }
*/

    items = (query, callback) => {
        this.parent(query, (rows, size, schema) => {
            this.dispatchEvent(new CustomEvent("load", {detail : {rows : rows, size : size, schema : schema}}))
            if (! isEqual(schema, this.schema)) {
                this.schema = schema;
            }
            window.setTimeout(() => {
                callback(rows, size)

                let table = this.querySelector("table");
                for (const [property, value] of Object.entries(this.schema.properties.rows.items.properties)) {
                    for (const column of table.columns) {
                        if (column.path === property) {
                            let resovled = column.resolve;
                            switch (value.widget) {
                                case "lazy-select" : {
                                    resovled.search = resovled.search || undefined
                                } break;
                                case "lazy-multi-select" : {
                                    resovled.search = resovled.search || []
                                } break;
                                case "datetime-local" : {
                                    resovled.search = resovled.search || {from : "", to : ""}
                                } break;
                                case "date" : {
                                    resovled.search = resovled.search || {from : "", to : ""}
                                } break;
                                default : {
                                    resovled.search = resovled.search || "";
                                }
                            }
                        }
                    }
                }

            })
        })
    }

    attributeChangedCallback(name, oldValue, newValue) {
        switch (name) {
            case "items" : {
                this.parent = newValue;
            }
        }
    }

    static get observedAttributes() {
        return [
            {
                name : "items",
                binding : "input"
            }
        ]
    }

    static get components() {
        return [MatTable, MetaColumn, MatTableExtension, MatTableSearch, MetaTableFilter]
    }

    static get template() {
        return libraryLoader("simplicity-material/components/meta/meta-table.html")
    }

}

export default customComponents.define("meta-table", MetaTable)