import {customComponents} from "../library/simplicity/simplicity.js";
import {loader} from "../library/simplicity/util/loader.js";
import DomRouter from "../library/simplicity/directives/dom-router.js";
import MatToolbar from "../library/simplicity/components/navigation/mat-toolbar.js";
import MatFooter from "../library/simplicity/components/navigation/mat-footer.js";
import MatLanguage from "../library/simplicity/components/system/mat-language.js";
import MatTaskbar from "../library/simplicity/components/system/mat-taskbar.js";
import DomA from "../library/simplicity/directives/dom-a.js";
import MatDrawer from "../library/simplicity/components/navigation/mat-drawer.js";
import MatDrawerContent from "../library/simplicity/components/navigation/mat-drawer-content.js";
import MatDrawerContainer from "../library/simplicity/components/navigation/mat-drawer-container.js";
import {routes} from "./routes.js";

class App extends HTMLElement {

    open = true;

    get app() {
        return this;
    }

    static get routes() {
        return routes;
    }

    static get components() {
        return [DomRouter, MatToolbar, MatFooter, MatLanguage, MatTaskbar, DomA, MatDrawer, MatDrawerContent, MatDrawerContainer];
    }

    static get template() {
        return loader("blomst/app.html")
    }

}

export default customComponents.define("app-blomst", App)