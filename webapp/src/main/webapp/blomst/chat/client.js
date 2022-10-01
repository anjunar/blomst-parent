import {customViews} from "../../library/simplicity-core/simplicity.js";
import MatInputContainer from "../../library/simplicity-material/components/form/container/mat-input-container.js";
import DomInput from "../../library/simplicity-core/directives/dom-input.js";
import {loader} from "../../library/simplicity-core/processors/loader-processor.js";
import {broadCaster} from "../socket.js";
import {Membrane} from "../../library/simplicity-core/services/tools.js";
import MetaTable from "../../library/simplicity-material/components/meta/meta-table.js";

class Client extends HTMLElement {

    user = null;

    model = {
        to: [],
        text: ""
    };

    typing = null;

    messages(query, callback) {
        let url = new URL("service/social/chat/messages", `${window.location.protocol}//${window.location.host}/`);
        url.searchParams.set("index", query.index);
        url.searchParams.set("limit", query.limit);
        url.searchParams.set("sort", "created:desc")
        url.searchParams.set("participants", this.user.id);

        for (const uuid of this.model.to) {
            url.searchParams.append("participants", uuid);
        }

        fetch(url.toString())
            .then(response => response.json())
            .then(response => callback(response.rows, response.size, response.$schema))
    }

    initialize() {
        let onTextMessage = () => {
            let table = this.querySelector("table");
            table.load();
        };

        let onStartTyping = (data) => {
            this.typing = data;
        }

        let onEndTyping = () => {
            this.typing = null;
        }

        broadCaster.register("chat-message", onTextMessage);
        broadCaster.register("chat-start-typing", onStartTyping)
        broadCaster.register("chat-end-typing", onEndTyping)

        Client.prototype.destroy = () => {
            broadCaster.unregister("chat-message", onTextMessage)
            broadCaster.unregister("chat-start-typing", onStartTyping)
            broadCaster.unregister("chat-end-typing", onEndTyping)
        }

        let typing = false;
        Membrane.track(this.model, {
            property : "text",
            element : this,
            handler : (value) => {
                if (value) {
                    if (! typing) {
                        broadCaster.fire("chat-start-typing", this.model)
                        typing = true;
                    }
                } else {
                    if (typing) {
                        broadCaster.fire("chat-end-typing", this.model)
                        typing = false;
                    }
                }
            }
        })
    }

    send() {
        fetch("service/social/chat/messages/message", {
            method: "POST",
            body: JSON.stringify(this.model),
            headers: {'Content-Type': 'application/json'}
        })
            .then(response => response.json())
            .then(_ => {
                let table = this.querySelector("table");
                table.load();
                this.model.text = "";
            })
    }

    static get components() {
        return [MatInputContainer, DomInput, MetaTable]
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