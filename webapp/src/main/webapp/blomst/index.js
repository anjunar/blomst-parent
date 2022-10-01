import {customViews} from "../library/simplicity-core/simplicity.js";
import {loader} from "../library/simplicity-core/processors/loader-processor.js";

class BlomstIndex extends HTMLElement {

    static get template() {
        return loader("blomst/index.html")
    }

}

export default customViews.define({
    name : "blomst-index",
    class : BlomstIndex
})