import {customComponents} from "../../../../simplicity-core/simplicity.js";
import {libraryLoader} from "../../../../simplicity-core/processors/loader-processor.js";
import MatInputContainer from "../../form/container/mat-input-container.js";
import DomInput from "../../../../simplicity-core/directives/dom-input.js";
import {Membrane} from "../../../../simplicity-core/services/tools.js";

class MetaInputInput extends HTMLElement {

    property;
    schema;

    initialize() {
        let input = this.querySelector("input");
        let validators = this.schema.validators;

        if (validators.notBlank || validators.notNull) {
            input.required = true;
        }
        if (validators.size) {
            input.maxLength = validators.size.max;
            input.minLength = validators.size.min;
        }

        switch (this.schema.type) {
            case "float" : {
                input.step = "0.0000001"
            } break
            case "double" : {
                input.step = "0.0000000000001"
            } break;
            default : {
                input.step = "1"
            }
        }

        input.checkValidity();
        input.dispatchEvent(new Event("input"))

        Membrane.track(input, {
            property : "dirty",
            element : this,
            handler : (value) => {
                this.schema.dirty = value;
            }
        })
    }

    attributeChangedCallback(name, oldValue, newValue) {
        switch (name) {
            case "schema" : {
                this.schema = newValue;
            } break;
            case "property" : {
                this.property = newValue;
            } break;
        }
    }

    static get observedAttributes() {
        return [
            {
                name: "schema",
                binding: "input"
            }, {
                name: "property",
                binding: "input"
            }
        ]
    }

    static get components() {
        return [MatInputContainer, DomInput]
    }

    static get template() {
        return libraryLoader("simplicity-material/components/meta/meta-input/meta-input-input.html")
    }

}

export default customComponents.define("meta-input-input", MetaInputInput);