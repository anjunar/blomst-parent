import {routes as security} from "./security/routes.js"

export const routes = {
    children : {
        "index" : {
            file : "blomst/index.js"
        },
        "security" : {
            children: security
        }
    }
}