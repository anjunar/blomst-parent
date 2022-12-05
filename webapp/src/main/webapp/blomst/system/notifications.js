import {customViews} from "../../library/simplicity/simplicity.js";
import {loader} from "../../library/simplicity/util/loader.js";
import MatList from "../../library/simplicity/components/table/mat-list.js";
import DomInput from "../../library/simplicity/directives/dom-input.js";

class Notifications extends HTMLElement {

    notifications(query, callback) {
        fetch(`service/system/notifications?index=${query.index}&limit=${query.limit}`)
            .then(response => response.json())
            .then(response => {
                callback(response.rows, response.size)
            })
    }

    onClick(notification) {
        if (! notification.acknowledge) {
            fetch(`service/system/notifications/notification/active?id=${notification.id}`, {method : "POST"})
                .then(() => {
                    let hash = `service/control/users/user/connections/connection/create?to=${notification.to.id}`;
                    window.location.hash = `#/navigator/form?link=${encodeURIComponent(hash)}`;
                    let systemNotification = document.querySelector("system-notification");
                    systemNotification.onNewNotification();
                })
        }
    }

    static get components() {
        return [MatList, DomInput]
    }

    static get template() {
        return loader("blomst/system/notifications.html")
    }

}

export default customViews.define({
    name : "system-notifications",
    class : Notifications
})