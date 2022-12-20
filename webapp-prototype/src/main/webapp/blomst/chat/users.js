import {customViews} from "../../library/simplicity/simplicity.js";
import MatInputContainer from "../../library/simplicity/components/form/container/mat-input-container.js";
import DomInput from "../../library/simplicity/directives/dom-input.js";
import {windowManager} from "../../library/simplicity/manager/window-manager.js";
import {broadCaster} from "../socket.js";
import MetaTable from "../../library/simplicity/components/meta/meta-table.js";
import {loader} from "../../library/simplicity/util/loader.js";

class Users extends HTMLElement {

    user = null;

    onlineUsers(query, callback) {
        fetch(`service/social/chat/online?index=${query.index}&limit=${query.limit}&from=${this.user.form.id}`)
            .then(response => response.json())
            .then(response => callback(response.rows, response.size, response.$schema))
    }

    initialize() {

        let onStatus = () => {
            let table = this.querySelector("mat-table");
            table.load();
        };

        let onTextMessage = (event) => {
            let participants = event["participants"];
            let indexOf = participants.indexOf(this.user.id);
            participants.splice(indexOf, 1);

            windowManager.openWindow("blomst/chat/client.js", {
                singleton: true,
                data: {
                    model: {
                        to: participants,
                        from: this.user.id,
                        text: ""
                    }
                }
            })
        };

        broadCaster.register("chat-status", onStatus)
        broadCaster.register("chat-message", onTextMessage)

        Users.prototype.destroy = () => {
            broadCaster.unregister("chat-status", onStatus)
            broadCaster.unregister("chat-message", onTextMessage)
        }
    }

    show(event) {
        windowManager.openWindow("blomst/chat/client.js", {
            singleton: true,
            data: {
                model: {
                    from: this.user.form.id,
                    to: [event.detail.id],
                    text: ""
                }
            }
        })
    }

    static get components() {
        return [MatInputContainer, DomInput, MetaTable]
    }

    static get template() {
        return loader("blomst/chat/users.html")
    }

}

export default customViews.define({
    name: "chat-users",
    header: "Chat Users",
    class: Users,
    guard(activeRoute) {
        return {
            user: fetch("service")
                .then(response => response.json())
        }
    }
})