import {customViews} from "../../library/simplicity/simplicity.js";
import {loader} from "../../library/simplicity/util/loader.js";
import MetaForm from "../../library/simplicity/components/meta/meta-form.js";
import MetaInput from "../../library/simplicity/components/meta/meta-input.js";

class Login extends HTMLElement {

    model;

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

    actions(links) {
        return Object.values(links)
            .filter((link) => link.method !== "GET")
    }

    static get components() {
        return [MetaForm, MetaInput];
    }

    static get template() {
        return loader("blomst/security/login.html")
    }


}

export default customViews.define({
    name: "security-login",
    class: Login,
    guard(activeRoute) {
        return {
            model: fetch("service/security/login")
                .then(response => response.json())
        }
    }
})