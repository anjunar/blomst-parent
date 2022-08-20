import {customViews} from "../../library/simplicity-core/simplicity.js";
import MatInputContainer from "../../library/simplicity-material/components/form/container/mat-input-container.js";
import DomInput from "../../library/simplicity-core/directives/dom-input.js";
import {loader} from "../../library/simplicity-core/processors/loader-processor.js";

class Client extends HTMLElement {

    messages = [];

    model = {
        type: "text-message",
        to: [],
        text: ""
    };

    initialize() {
        this.socket.addEventListener("message", (event) => {
            let data = JSON.parse(event.data);
            switch (data.type) {
                case "users" : {
                }
                    break;
                case "status" : {
                }
                    break;
                default : {
                    this.messages.push(data)
                }
            }
        })
    }

    send() {
        this.socket.send(JSON.stringify(this.model));
        this.messages.push(JSON.parse(JSON.stringify(this.model)));
    }

    static get components() {
        return [MatInputContainer, DomInput]
    }

    static get template() {
        return loader("hive/chat/client.html")
    }

}

export default customViews.define({
    name: "chat-client",
    class: Client,
    header: "Client"
})