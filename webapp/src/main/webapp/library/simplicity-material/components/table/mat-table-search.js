import {customComponents} from "../../../simplicity-core/simplicity.js";

class MatTableSearch extends HTMLElement {

    path
    schema;

    get sortable() {
        return this.schema.type === "string"
    }

    attributeChangedCallback(name, oldValue, newValue) {
        switch (name) {
            case "path" : {
                this.path = newValue;
            }
                break;
            case "schema" : {
                this.schema = newValue
            }
        }
    }

    static get observedAttributes() {
        return [
            {
                name: "path",
                binding: "input"
            }, {
                name: "schema",
                binding: "input"
            }
        ]
    }

    static get components() {
        return []
    }

}

export default customComponents.define("mat-table-search", MatTableSearch)