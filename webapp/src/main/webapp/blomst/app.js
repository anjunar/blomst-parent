import {customComponents} from "../library/simplicity-core/simplicity.js";
import {loader} from "../library/simplicity-core/processors/loader-processor.js";
import DomRouter from "../library/simplicity-core/directives/dom-router.js"
import MatToolbar from "../library/simplicity-material/components/navigation/mat-toolbar.js";
import MatFooter from "../library/simplicity-material/components/navigation/mat-footer.js";
import MatLanguage from "../library/simplicity-material/components/system/mat-language.js";
import MatTaskbar from "../library/simplicity-material/components/system/mat-taskbar.js";
import {routes} from "./routes.js"
import DomA from "../library/simplicity-core/directives/dom-a.js";
import {broadCaster} from "./socket.js";

class HiveApp extends HTMLElement {

    get navigatorLink() {
        return `#/navigator/form?link=${encodeURIComponent("service")}`
    }

    static get routes() {
        return routes;
    }

    static get components() {
        return [DomRouter, MatToolbar, MatFooter, MatLanguage, MatTaskbar, DomA];
    }

    static get template() {
        return loader("blomst/app.html");
    }
}

export default customComponents.define("app-hive", HiveApp);

