import {customViews} from "../../library/simplicity-core/simplicity.js";
import {loader} from "../../library/simplicity-core/processors/loader-processor.js";
import MetaForm from "../../library/simplicity-material/components/meta/meta-form.js";
import MetaInput from "../../library/simplicity-material/components/meta/meta-input.js";

class Table extends HTMLElement {

    model;

    items(query, callback) {
        let link = decodeURIComponent(this.queryParams.link);
        let url = new URL(link, `${window.location.protocol}//${window.location.host}/app/`);
        url.searchParams.set("index", query.index);
        url.searchParams.set("limit", query.limit);
        fetch(url.toString())
            .then(response => response.json())
            .then((response) => {
                callback(response.rows, response.size, response.$schema)
            })
    }

    onRowClick(event) {
        let row = event.detail;
        let link = row.$schema.links.read;
        history.pushState(null, null, `navigator?link=${encodeURIComponent(link.url)}`)
        window.dispatchEvent(new Event("popstate"))
    }

    onAction(link) {
        fetch(link.url, {
            body: JSON.stringify(this.model),
            method: link.method,
            headers: new Headers({'content-type': 'application/json'}),
        })
            .then(response => response.json())
            .then(response => {
                /*
                                const tx = db.transaction("activities", "readwrite");
                                const store = tx.objectStore("activities");
                                store.put({
                                    created : Date.now(),
                                    description : link.description,
                                    content : JSON.stringify(this.model)
                                });
                */
            })
    }

    get link() {
        return decodeURIComponent(this.queryParams.link);
    }

    hrefLink(link) {
        if (link.type === "table") {
            return `navigator/table?link=${encodeURIComponent(link.url)}`;
        }
        return `navigator/form?link=${encodeURIComponent(link.url)}`;
    }

    links(links) {
        return Object.values(links).filter((link) => link.method === "GET");
    }

    actions(links) {
        return Object.values(links).filter((link) => link.method !== "GET");
    }

    static get components() {
        return [MetaForm, MetaInput];
    }

    static get template() {
        return loader("hive/navigator/form.html");
    }


}

export default customViews.define({
    name: "app-navigator-form",
    class: Table,
    guard(activeRoute) {
        return {
            model: fetch(decodeURIComponent(activeRoute.queryParams.link))
                .then(response => response.json())
        }
    }
})