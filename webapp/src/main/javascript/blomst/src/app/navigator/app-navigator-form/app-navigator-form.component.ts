import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AppNavigatorService} from "../app-navigator.service";
import {Link} from "angular2-simplicity";

@Component({
  selector: 'app-navigator-form',
  templateUrl: 'app-navigator-form.component.html',
  styleUrls: ['app-navigator-form.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class AppNavigatorFormComponent {

  model : any;

  constructor(private router : Router, private route : ActivatedRoute, protected service : AppNavigatorService) {
    let data :any = route.data;
    this.model = data["value"].model
    service.links = Object
      .entries(this.model.$schema.links)
      .filter(([key , value]) => { let object : any = value; return object.method === "GET"})
      .reduce((prev, [key, value]) => {
        prev[key] = value;
        return prev;
      }, {} as any)
  }


  onSubmit(event : {link : {key : string, value : Link}, model : any}) {
    let body = JSON.stringify(event.model);
    let method = event.link.value.method;
    let headers = {"content-type" : "application/json"};
    fetch(event.link.value.url, {method : method, body : body, headers : headers})
      .then((response) => response.json())
      .then((response) => {
        if (response.$schema.links.redirect) {
          let link = response.$schema.links.redirect;
          this.router.navigate(["navigator/" + link.type], { queryParams : {link : btoa(link.url)} })
        }
      })
  }
}
