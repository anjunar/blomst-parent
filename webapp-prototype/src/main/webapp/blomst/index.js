import {customViews} from "../library/simplicity/simplicity.js";
import {loader} from "../library/simplicity/util/loader.js";

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