import {routes as navigator} from "./navigator/routes.js"

export const routes = {
    children : {
        "index" : {
            file : "blomst/index.js"
        },
        "navigator" : {
            children: navigator
        },
        "system" : {
            file : "blomst/system/activities.js"
        },
        "notifications" : {
            file : "blomst/system/notifications.js"
        }
    }
}