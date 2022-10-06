import {customViews} from "../library/simplicity-core/simplicity.js";
import {loader} from "../library/simplicity-core/processors/loader-processor.js";

class BlomstIndex extends HTMLElement {

    get title() {
        if (window.location.host.indexOf("poseidon") > -1) {
            return "Poseidon"
        } else {
            return "Blomst"
        }
    }

    static get template() {
        return loader("blomst/index.html")
    }

}

export default customViews.define({
    name : "blomst-index",
    class : BlomstIndex
})