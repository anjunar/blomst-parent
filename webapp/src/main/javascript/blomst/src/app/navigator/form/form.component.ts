import {Component, OnDestroy, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AppNavigatorService} from "../app-navigator.service";
import {Link, SelectQuery, WindowManagerService} from "ng2-simplicity";
import {VisibilityComponent} from "../../shared/visibility/visibility.component";
import {KeyValue} from "@angular/common";

@Component({
  selector: 'app-form',
  templateUrl: 'form.component.html',
  styleUrls: ['form.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class FormComponent {

  links : any;

  model : any;

  header! : string

  constructor(private router : Router, private route : ActivatedRoute, private windowManager : WindowManagerService) {
    let data :any = route.data;
    this.model = data["value"].model
    this.links = Object
      .entries(this.model.$schema.links)
      .filter(([key , value]) => { let object : any = value; return object.method === "GET"})
      .reduce((prev, [key, value]) => {
        prev[key] = value;
        return prev;
      }, {} as any)

    route.queryParams.subscribe(params => {
      this.header = atob(params["link"])
    })
  }

  onVisibilityColumnClick(event: Event, property: string, model : any) {
    event.stopPropagation();

    let url = `service/shared/visibility/column?property=${property}&class=${model["@type"]}&id=${model.id}`;

    secureFetch(url)
      .then(response => {
        let windowRef = this.windowManager.create(VisibilityComponent, {header: "Visibility", width: "300px"});
        windowRef.instance.property = property;
        windowRef.instance.clazz = model["@type"];
        windowRef.instance.id = model.id;
        windowRef.instance.model = response;
      })
  }

  onSubmit(event : {link : {key : string, value : Link}, model : any}) {
    let method = event.link.value.method;
    secureFetch(event.link.value.url, method, event.model.form)
      .then((response) => {
        if (response.$schema.links.redirect) {
          let link = response.$schema.links.redirect;
          this.router.navigate(["navigator/" + link.type], { queryParams : {link : btoa(link.url)} })
        }
      })
  }

  toBase64(value: any) {
    return btoa(value)
  }

  originalOrder = (a: KeyValue<number, string>, b: KeyValue<number, string>): number => {
    return 0;
  }

}
