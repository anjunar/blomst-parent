import {customViews} from "../../library/simplicity-core/simplicity.js";
import MatInputContainer from "../../library/simplicity-material/components/form/container/mat-input-container.js";
import DomInput from "../../library/simplicity-core/directives/dom-input.js";
import {loader} from "../../library/simplicity-core/processors/loader-processor.js";
import {isEqual} from "../../library/simplicity-core/services/tools.js";
import {windowManager} from "../../library/simplicity-material/manager/window-manager.js";

class Users extends HTMLElement {

    socket = new WebSocket("ws://localhost:8080/app/socket/chat")

    user = null;

    users = [];

    initialize() {
        this.socket.addEventListener("message", (event) => {
            let data = JSON.parse(event.data);
            switch (data.type) {
                case "users" : {
                    this.users = data.list;
                } break;
                case "status" : {
                    if (data.status === "ONLINE") {
                        let find = this.users.find(user => isEqual(user, data.user));
                        if (find) {
                            let indexOf = this.users.indexOf(find);
                            this.users.splice(indexOf,1)
                        } else {
                            this.users.push(data.user);
                        }
                    } else {
                        let find = this.users.find(user => isEqual(user, data.user));
                        if (find) {
                            let indexOf = this.users.indexOf(find);
                            this.users.splice(indexOf,1)
                        }
                    }
                }  break;
                default : {
                    windowManager.openWindow("hive/chat/client.js", {
                        singleton : true,
                        data: {
                            socket: this.socket,
                            model : {
                                to : [data.from.id],
                                from : this.user,
                                type : "text-message",
                                text : ""
                            },
                            messages : [data]
                        }
                    })
                }
            }
        })

        this.addEventListener("removed", () => {
            this.socket.close();
        });
    }

    show(model) {
        windowManager.openWindow("hive/chat/client.js", {
            singleton : true,
            data: {
                socket: this.socket,
                model : {
                    type : "text-message",
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
        return loader("hive/chat/users.html")
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