import {customViews} from "../../library/simplicity-core/simplicity.js";
import MatInputContainer from "../../library/simplicity-material/components/form/container/mat-input-container.js";
import DomInput from "../../library/simplicity-core/directives/dom-input.js";
import {loader} from "../../library/simplicity-core/processors/loader-processor.js";
import {broadCaster} from "../socket.js";
import MatTable from "../../library/simplicity-material/components/table/mat-table.js";

class Client extends HTMLElement {

    user = null;

    model = {
        to: [],
        text: ""
    };

    messages(query, callback) {
        let url = new URL("service/social/chat/messages", `${window.location.protocol}//${window.location.host}/app/`);
        url.searchParams.set("index", query.index);
        url.searchParams.set("limit", query.limit);
        url.searchParams.set("sort", "created:desc")
        url.searchParams.set("participants", this.user.id);

        for (const uuid of this.model.to) {
            url.searchParams.append("participants", uuid);
        }

        fetch(url.toString())
            .then(response => response.json())
            .then(response => callback(response.rows, response.size))
    }

    initialize() {
        let onTextMessage = (data) => {
            let table = this.querySelector("table");
            table.load();
        };

        broadCaster.register("chat-text-message", onTextMessage)

        Client.prototype.destroy = () => {
            broadCaster.unregister("chat-text-message", onTextMessage)
        }
    }

    send() {
        broadCaster.fire("chat-text-message", this.model);
    }

    static get components() {
        return [MatInputContainer, DomInput, MatTable]
    }

    static get template() {
        return loader("blomst/chat/client.html")
    }

}

export default customViews.define({
    name: "chat-client",
    class: Client,
    header: "Client",
    guard(activeRoute) {
        return {
            user: fetch("service").then(response => response.json())
        }
    }
})