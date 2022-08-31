import {customViews} from "../../library/simplicity-core/simplicity.js";
import MatInputContainer from "../../library/simplicity-material/components/form/container/mat-input-container.js";
import DomInput from "../../library/simplicity-core/directives/dom-input.js";
import {loader} from "../../library/simplicity-core/processors/loader-processor.js";
import {isEqual} from "../../library/simplicity-core/services/tools.js";
import {windowManager} from "../../library/simplicity-material/manager/window-manager.js";
import {broadCaster} from "../socket.js";

class Users extends HTMLElement {

    user = null;
    users = [];

    initialize() {

        let onUsersUpdate = (data) => {
            this.users = data.list;
        };

        let onStatus = (data) => {
            if (data.status === "ONLINE") {
                let find = this.users.find(user => isEqual(user, data.user));
                if (! find) {
                    this.users.push(data.user);
                }
            } else {
                let find = this.users.find(user => isEqual(user, data.user));
                if (find) {
                    let indexOf = this.users.indexOf(find);
                    this.users.splice(indexOf,1)
                }
            }
        };

        let onTextMessage = (data) => {
            windowManager.openWindow("hive/chat/client.js", {
                singleton : true,
                data: {
                    model : {
                        to : [data.from.id],
                        from : this.user,
                        text : ""
                    },
                    messages : [data]
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

    show(model) {
        windowManager.openWindow("hive/chat/client.js", {
            singleton : true,
            data: {
                model : {
                    from : this.user,
                    to : [model.id],
                    text : ""
                }
            }
        })
    }

    static get components() {
        return [MatInputContainer, DomInput]
    }

    static get template() {
        return loader("blomst/chat/users.html")
    }

}

export default customViews.define({
    name: "chat-users",
    header : "Chat Users",
    class: Users,
    guard(activeRoute) {
        return {
            user : fetch("service")
                .then(response => response.json())
        }
    }
})