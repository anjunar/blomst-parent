import {customViews} from "../../library/simplicity/simplicity.js";
import MatTable from "../../library/simplicity/components/table/mat-table.js";
import MetaForm from "../../library/simplicity/components/meta/meta-form.js";
import {dateTimeFormat} from "../../library/simplicity/util/tools.js";
import {loader} from "../../library/simplicity/util/loader.js";

class Activities extends HTMLElement {

    date(long) {
        if (long) {
            return dateTimeFormat(new Date(long))
        }
        return ""
    }

    items(query, callback) {
        let transaction = db.transaction(['activities'], 'readonly');
        let objectStore = transaction.objectStore('activities');
        let index = objectStore.index('created');
        let countRequest = objectStore.count();
        countRequest.onsuccess = function() {
            let size = countRequest.result;

            let openCursor = index.openCursor(null, "prev");
            let result = [];
            let advanced = false;
            let count = 0;

            openCursor.onsuccess = (event) => {
                let cursor = event.target.result;
                if (! advanced && query.index > 0) {
                    cursor.advance(query.index);
                    advanced = true;
                    return;
                }

                count++

                if (count <= query.limit) {
                    if (cursor) {
                        result.push({
                            created : cursor.value.created,
                            description : cursor.value.description,
                            content : JSON.parse(cursor.value.content)
                        })
                        cursor.continue();
                    }
                } else {
                    callback(result, size);
                }
            }

        }
    }

    static get components() {
        return [MatTable, MetaForm];
    }

    static get template() {
        return loader("hive/system/activities.html");
    }

}

export default customViews.define({
    name: "system-activities",
    class: Activities
})