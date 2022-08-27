import {routes as navigator} from "./navigator/routes.js"

export const routes = {
    children : {
        "navigator" : {
            children: navigator
        },
        "system" : {
            file : "hive/system/activities.js"
        }
    }
}