import {customViews} from "../library/simplicity/simplicity.js";
import {loader} from "../library/simplicity/util/loader.js";
import MatCarouselItem from "../library/simplicity/components/navigation/mat-carousel-item.js";
import MatCarousel from "../library/simplicity/components/navigation/mat-carousel.js";
import {dateTimeFormat} from "../library/simplicity/util/tools.js";
import MatList from "../library/simplicity/components/table/mat-list.js";
import MetaForm from "../library/simplicity/components/meta/meta-form.js";
import MetaInput from "../library/simplicity/components/meta/meta-input.js";

class Blomst extends HTMLElement {

    comment;

    postQuery(query, callback) {
        fetch(`service/home/timeline?index=${query.index}&limit=${query.limit}`)
            .then(response => response.json())
            .then(response => {
                callback(response.rows, response.size);
            })
    }

    commentQuery(id) {
        return (query, callback) => {
            fetch(`service/home/timeline/post/comments?post=${id}&index=${query.index}&limit=${query.limit}`)
                .then(response => response.json())
                .then(response => {
                    callback(response.rows, response.size);
                })
        }
    }

    commentSave(event) {
        let value = event.target.value;
        if (event.key === "Enter") {
            fetch(`service/home/timeline/post/comments/comment`, {method : "POST", body : JSON.stringify(this.comment)})
                .then(response => response.json())
                .then(response => console.log(response))
        }
    }

    onClickAdd(id) {
        let flipCard = this.querySelector(".container");
        flipCard.classList.add("flip-card");

        fetch(`service/home/timeline/post/comments/comment/create?post=${id}`)
            .then(response => response.json())
            .then(response => {
                this.comment = response;
            })
    }

    onClickRemove() {
        let flipCard = this.querySelector(".container");
        flipCard.classList.remove("flip-card")
    }

    dateTimeFormat(value) {
        return dateTimeFormat(value, "en");
    }

    static get components() {
        return [MatCarouselItem, MatCarousel, MatList, MetaForm, MetaInput]
    }

    static get template() {
        return loader("blomst/index.html")
    }

}

export default customViews.define({
    name: "blomst-index",
    class: Blomst
})