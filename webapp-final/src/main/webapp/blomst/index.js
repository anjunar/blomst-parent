import {customViews} from "../library/simplicity/simplicity.js";
import {loader} from "../library/simplicity/util/loader.js";
import MatCarouselItem from "../library/simplicity/components/navigation/mat-carousel-item.js";
import MatCarousel from "../library/simplicity/components/navigation/mat-carousel.js";
import {dateTimeFormat} from "../library/simplicity/util/tools.js";

class Blomst extends HTMLElement {

    query(query, callback) {
        fetch(`service/home/timeline?index=${query.index}&limit=${query.limit}`)
            .then(response => response.json())
            .then(response => {
                callback(response.rows, response.size);
            })

    }

    dateTimeFormat(value) {
        return dateTimeFormat(value, "en");
    }

    static get components() {
        return [MatCarouselItem, MatCarousel]
    }

    static get template() {
        return loader("blomst/index.html")
    }

}

export default customViews.define({
    name: "blomst-index",
    class: Blomst
})