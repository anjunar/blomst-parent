import {customComponents} from "../../../simplicity/simplicity.js";
import {dateFormat, dateTimeFormat} from "../../../simplicity/util/tools.js";
import {libraryLoader} from "../../util/loader.js";

class MetaColumn extends HTMLElement {

    model = "test";
    schema = {
        type : "string"
    };

    multiSelect(model, meta) {
        if (model && meta) {
            return model.map((item) => Object
                .entries(meta.items.properties)
                .filter(([name, property]) => property.naming)
                .map(([name, property]) => item[name])
                .join(" ")
            ).join(" ")
        }
        return "";
    }

    select(model, meta) {
        if (model && meta) {
            return Object
                .entries(meta.properties)
                .filter(([name, property]) => property.naming)
                .map(([name, property]) => model[name])
                .join(" ")
        }
        return "";
    }

    dateTime(value, meta) {
        if (value) {
            return dateTimeFormat(value, this.app.language)
        }
        return "";
    }

    date(value, meta) {
        if (value) {
            return dateFormat(value, this.app.language)
        }
        return "";
    }

    attributeChangedCallback(name, oldValue, newValue) {
        switch (name) {
            case "model" : {
                this.model = newValue;
            }
                break;
            case "schema" : {
                this.schema = newValue;
            }
                break;
        }
    }

    static get observedAttributes() {
        return [
            {
                name: "model",
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

    static get template() {
        return libraryLoader("simplicity/components/meta/meta-column.html")
    }


}

export default customComponents.define("meta-column", MetaColumn);