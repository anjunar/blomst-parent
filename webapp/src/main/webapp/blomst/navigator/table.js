import {customViews} from "../../library/simplicity-core/simplicity.js";
import {loader} from "../../library/simplicity-core/processors/loader-processor.js";
import MetaTable from "../../library/simplicity-material/components/meta/meta-table.js";
import MatDrawer from "../../library/simplicity-material/components/navigation/mat-drawer.js";
import MatDrawerContainer from "../../library/simplicity-material/components/navigation/mat-drawer-container.js";
import MatDrawerContent from "../../library/simplicity-material/components/navigation/mat-drawer-content.js";

class Table extends HTMLElement {

    model;

    schema = {
        links : {}
    }

    open = true;

    items(query, callback) {
        let link = decodeURIComponent(this.queryParams.link);
        let url = new URL(link, `${window.location.protocol}//${window.location.host}/app/`);
        url.searchParams.set("index", query.index);
        url.searchParams.set("limit", query.limit);

        for (const [key, value] of Object.entries(query.filter)) {
            if (value instanceof Array) {
                for (const element of value) {
                    url.searchParams.append(key, element.id)
                }
            } else if (value instanceof Object) {
                if (value.from && value.to) {
                    url.searchParams.set(key, `from${value.from}to${value.to}`)
                } else {
                    url.searchParams.set(key, value.id)
                }
            } else {
                url.searchParams.set(key, value);
            }
        }

        fetch(url.toString())
            .then(response => response.json())
            .then((response) => {
                callback(response.rows, response.size, response.$schema)
            })
    }

    onRowClick(event) {
        let row = event.detail;
        let link = row.$schema.links.read;
        window.location.hash = `/navigator/form?link=${encodeURIComponent(link.url)}`
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

    onLoad(event) {
        this.schema = event.detail.schema;
    }

    get link() {
        return decodeURIComponent(this.queryParams.link);
    }

    hrefLink(link) {
        if (link.type === "table") {
            return `#/navigator/table?link=${encodeURIComponent(link.url)}`;
        }
        return `#/navigator/form?link=${encodeURIComponent(link.url)}`;
    }

    links(links) {
        return Object.values(links).filter((link) => link.method === "GET");
    }

    actions(links) {
        return Object.values(links).filter((link) => link.method !== "GET");
    }

    static get components() {
        return [MetaTable, MatDrawer, MatDrawerContainer, MatDrawerContent];
    }

    static get template() {
        return loader("blomst/navigator/table.html");
    }


}

export default customViews.define({
    name: "app-navigator-table",
    class: Table
})