import {VisibilityComponent} from "./shared/visibility/visibility.component";
import {WindowManagerService} from "angular2-simplicity";
import {ActivatedRoute} from "@angular/router";

export interface Form<E> {
  $schema : any
  form : E
}

export class AppView {

  constructor(protected route: ActivatedRoute, protected windowManager: WindowManagerService) {
    let data: any = route.data;

    let jsons: any[] = data.value["urls"];
    let self :any = this;
    if (jsons) {
      jsons.forEach((json: any, index) => {
        let property = json["name"];
        self[property] = data.value["model"][index];
      })
    }
  }

  onVisibilityColumnClick(event: Event, property: string, model : any) {
    event.stopPropagation();

    let url = `service/shared/visibility/column?property=${property}&class=${model["@type"]}&id=${model.id}`;

    secureFetch(url)
      .then(response => response.json())
      .then(response => {
        let windowRef = this.windowManager.create(VisibilityComponent, {header: "Visibility", width: 300});
        windowRef.instance.property = property;
        windowRef.instance.clazz = model["@type"];
        windowRef.instance.id = model.id;
        windowRef.instance.model = response;
      })
  }

  onVisibilityRowClick(event: Event, model : any) {
    event.stopPropagation();

    let url = `service/shared/visibility/row?class=${model["@type"]}&id=${model.id}`;

    secureFetch(url)
      .then(response => response.json())
      .then(response => {
        let windowRef = this.windowManager.create(VisibilityComponent, {header: "Visibility", width: 300});
        windowRef.instance.clazz = model["@type"];
        windowRef.instance.id = model.id;
        windowRef.instance.model = response;
      })
  }


}
