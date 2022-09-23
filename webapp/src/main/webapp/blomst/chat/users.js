import {customViews} from "../../library/simplicity-core/simplicity.js";
import MatInputContainer from "../../library/simplicity-material/components/form/container/mat-input-container.js";
import DomInput from "../../library/simplicity-core/directives/dom-input.js";
import {loader} from "../../library/simplicity-core/processors/loader-processor.js";
import {windowManager} from "../../library/simplicity-material/manager/window-manager.js";
import {broadCaster} from "../socket.js";
import MatTable from "../../library/simplicity-material/components/table/mat-table.js";

class Users extends HTMLElement {

    user = null;

    onlineUsers(query, callback) {
        fetch(`service/social/chat/online?index=${query.index}&limit=${query.limit}`)
            .then(response => response.json())
            .then(response => callback(response.rows, response.size))
    }

    initialize() {

        let onUsersUpdate = (data) => {
            let table = this.querySelector("table");
            table.load();
        };

        let onStatus = (data) => {
            let table = this.querySelector("table");
            table.load();
        };

        let onTextMessage = (event) => {
            windowManager.openWindow("blomst/chat/client.js", {
                singleton: true,
                data: {
                    model: {
                        to: [event.from.id],
                        from: this.user,
                        text: ""
                    }
                }
            })
        };

        broadCaster.register("chat-users-update", onUsersUpdate)
        broadCaster.register("chat-status", onStatus)
        broadCaster.register("chat-text-message", onTextMessage)

        broadCaster.fire("chat-users-read")

        Users.prototype.destroy = () => {
            broadCaster.unregister("chat-users-update", onUsersUpdate)
            broadCaster.unregister("chat-status", onStatus)
            broadCaster.unregister("chat-text-message", onTextMessage)
        }
    }

    show(event) {
        windowManager.openWindow("blomst/chat/client.js", {
            singleton: true,
            data: {
                model: {
                    from: this.user,
                    to: [event.detail.id],
                    text: ""
                }
            }
        })
    }

    static get components() {
        return [MatInputContainer, DomInput, MatTable]
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