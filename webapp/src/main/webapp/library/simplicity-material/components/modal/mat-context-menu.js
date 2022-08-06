import {customComponents} from "../../../simplicity-core/simplicity.js";
import {libraryLoader} from "../../../simplicity-core/processors/loader-processor.js";

class MatContextMenu extends HTMLElement {

    initialize() {
        this.addEventListener("click", (event) => {
            event.stopPropagation();
            return false;
        })

        let listener = () => {
            this.remove();
        };

        window.addEventListener("click", listener)

        MatContextMenu.prototype.destroy = () => {
            window.removeEventListener("click", listener);
        }
    }

    static get components() {
        return []
    }

    static get template() {
        return libraryLoader("simplicity-material/components/modal/mat-context-menu.html")
    }

}

export default customComponents.define("mat-context-menu", MatContextMenu)