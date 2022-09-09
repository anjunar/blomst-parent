import {customViews} from "../../../simplicity-core/simplicity.js";
import {libraryLoader} from "../../../simplicity-core/processors/loader-processor.js";
import MatTabs from "../navigation/mat-tabs.js";
import MatTab from "../navigation/mat-tab.js";
import MatPages from "../navigation/mat-pages.js";
import MatPage from "../navigation/mat-page.js";
import DomInput from "../../../simplicity-core/directives/dom-input.js";
import MatCheckboxContainer from "../form/container/mat-checkbox-container.js";
import MatTable from "./mat-table.js";

class MatTableConfiguration extends HTMLElement {

    table;
    page = 0;

    left(event, index) {
        event.stopPropagation();
        this.table.left(index);
        this.page--;
        return false;
    }

    right(event, index) {
        event.stopPropagation();
        this.table.right(index);
        this.page++;
        return false;
    }

    tableColumns(query, callback) {
        let configuration = this.table.configuration(this.table.columns, true);
        let method = configuration.method();
        callback(method, method.length);

        configuration.resonator(() => {
            let configuration = this.table.configuration(this.table.columns, true);
            let method = configuration.method();
            callback(method, method.length);
        }, this);
    }

    static get components() {
        return [DomInput, MatTabs, MatTab, MatPages, MatPage, MatCheckboxContainer, MatTable]
    }

    static get template() {
        return libraryLoader("simplicity-material/components/table/mat-table-configuration.html")
    }
}

export default customViews.define({
    name : "mat-table-configuration",
    class : MatTableConfiguration,
    header : "Table Configuration"
})