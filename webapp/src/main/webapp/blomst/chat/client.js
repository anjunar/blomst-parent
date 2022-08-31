import {customViews} from "../../library/simplicity-core/simplicity.js";
import MatInputContainer from "../../library/simplicity-material/components/form/container/mat-input-container.js";
import DomInput from "../../library/simplicity-core/directives/dom-input.js";
import {loader} from "../../library/simplicity-core/processors/loader-processor.js";
import {broadCaster} from "../socket.js";

class Client extends HTMLElement {

    messages = [];

    model = {
        to: [],
        text: ""
    };

    initialize() {
        let onTextMessage = (data) => {
            this.messages.push(data)
        };

        broadCaster.register("chat-text-message", onTextMessage)

        Client.prototype.destroy = () => {
            broadCaster.unregister("chat-text-message", onTextMessage)
        }
    }

    send() {
        broadCaster.fire("chat-text-message", this.model);
        this.messages.push(JSON.parse(JSON.stringify(this.model)));
    }

    static get components() {
        return [MatInputContainer, DomInput]
    }

    static get template() {
        return loader("blomst/chat/client.html")
    }

}

export default customViews.define({
    name: "chat-client",
    class: Client,
    header: "Client"
})