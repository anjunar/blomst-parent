import {customComponents} from "../../../simplicity/simplicity.js";
import {libraryLoader} from "../../util/loader.js";
import {contentChildren} from "../../interpreters/json-interpreter.js";

class MatTabs extends HTMLElement {

    page = 0;
    container;

    tabs() {
        let method = () => {
            return Array.from(this.container.querySelectorAll("mat-tab"))
        }
        let resonator = (callback, element) => {
            let mutationObserver = new MutationObserver(() => {
                callback();
                this.dispatchEvent(new CustomEvent("page"))
            })

            mutationObserver.observe(this.container, {childList : true, subtree : true})

            element.addEventListener("removed", () => {
                mutationObserver.disconnect();
            })
        }
        return {method, resonator}
    }

    preInitialize() {
        this.container = contentChildren(this);
    }

    rendered(children) {
        for (const child of children) {
            let tab = child.querySelector("mat-tab")
            tab.selected = false;
            tab.addEventListener("click", (event) => {
                event.stopPropagation();
                for (const child of children) {
                    let tab = child.querySelector("mat-tab")
                    tab.selected = false;
                }
                tab.selected = true;
                this.page = children.indexOf(child);
                this.dispatchEvent(new CustomEvent("page"))
                return false;
            })
        }
        if (children.length > 0 && this.page > -1) {
            let child = children[this.page];
            let tab = child.querySelector("mat-tab")
            tab.selected = true;
        }
    }

    attributeChangedCallback(name, oldValue, newValue) {
        switch (name) {
            case "page" : {
                this.page = newValue;
            }
        }
    }

    static get observedAttributes() {
        return [
            {
                name: "page",
                binding: "two-way"
            }
        ]
    }

    static get components() {
        return []
    }

    static get template() {
        return libraryLoader("simplicity/components/navigation/mat-tabs.html")
    }
}

export default customComponents.define("mat-tabs", MatTabs);