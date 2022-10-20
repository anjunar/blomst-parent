import {customViews} from "../../library/simplicity-core/simplicity.js";
import {loader} from "../../library/simplicity-core/processors/loader-processor.js";
import MetaForm from "../../library/simplicity-material/components/meta/meta-form.js";
import MetaInput from "../../library/simplicity-material/components/meta/meta-input.js";
import MatDrawer from "../../library/simplicity-material/components/navigation/mat-drawer.js";
import MatDrawerContent from "../../library/simplicity-material/components/navigation/mat-drawer-content.js";
import MatDrawerContainer from "../../library/simplicity-material/components/navigation/mat-drawer-container.js";

class Table extends HTMLElement {

    model = null;
    open = true;

    onAction(link) {
        let form = this.querySelector("meta-form");
        if (form.validate()) {
            fetch(link.url, {
                body: JSON.stringify(this.model),
                method: link.method,
                headers: new Headers({'content-type': 'application/json'}),
            })
                .then(response => response.json())
                .then(response => {
                    let read = response.$schema.links.redirect;
                    if (read) {
                        if (read.type === "table") {
                            window.location.hash = `#/navigator/table?link=${encodeURIComponent(read.url)}`
                        } else {
                            window.location.hash = `#/navigator/form?link=${encodeURIComponent(read.url)}`
                        }
                    }
                })
        }
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
        return Object.values(links)
            .filter((link) => link.method === "GET")
    }

    actions(links) {
        return Object.values(links)
            .filter((link) => link.method !== "GET")
    }

    static get components() {
        return [MetaForm, MetaInput, MatDrawer, MatDrawerContent, MatDrawerContainer];
    }

    static get template() {
        return loader("blomst/navigator/form.html");
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