import {customComponents} from "../../library/simplicity/simplicity.js";
import {loader} from "../../library/simplicity/util/loader.js";
import {broadCaster} from "../socket.js";

class Notification extends HTMLElement {

    size = 0;

    onNewNotification() {
        fetch("service/system/notifications/notification/active")
            .then((response) => response.json())
            .then(response => {
                this.size = response.size;
            })
    }

    initialize() {
        this.onNewNotification();

        broadCaster.register("notification-new", this.onNewNotification)

        Notification.prototype.destroy = () => {
            broadCaster.unregister("notification-new", this.onNewNotification)
        }
    }

    static get template() {
        return loader("blomst/system/notification.html");
    }

}

export default customComponents.define("system-notification", Notification);