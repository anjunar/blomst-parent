import {customComponents} from "../library/simplicity/simplicity.js";
import DomRouter from "../library/simplicity/directives/dom-router.js"
import MatToolbar from "../library/simplicity/components/navigation/mat-toolbar.js";
import MatFooter from "../library/simplicity/components/navigation/mat-footer.js";
import MatLanguage from "../library/simplicity/components/system/mat-language.js";
import MatTaskbar from "../library/simplicity/components/system/mat-taskbar.js";
import {routes} from "./routes.js"
import DomA from "../library/simplicity/directives/dom-a.js";
import {loader} from "../library/simplicity/util/loader.js";
import Notification from "./system/notification.js";

class HiveApp extends HTMLElement {

    get app() {
        return this;
    }

    get title() {
        if (window.location.host.indexOf("poseidon") > -1) {
            return "Poseidon"
        } else {
            return "Blomst"
        }
    }

    get navigatorLink() {
        return `#/navigator/form?link=${encodeURIComponent("service")}`
    }

    static get routes() {
        return routes;
    }

    static get components() {
        return [DomRouter, MatToolbar, MatFooter, MatLanguage, MatTaskbar, DomA, Notification];
    }

    static get template() {
        return loader("blomst/app.html");
    }
}

export default customComponents.define("app-hive", HiveApp);

