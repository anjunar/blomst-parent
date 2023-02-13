import {Component, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {AppNavigatorService} from "../app-navigator.service";
import {Link, SelectQuery} from "angular2-simplicity";

@Component({
  selector: 'app-form',
  templateUrl: 'form.component.html',
  styleUrls: ['form.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class FormComponent {

  model : any;

  header! : string

  constructor(private router : Router, private route : ActivatedRoute, private service : AppNavigatorService) {
    let data :any = route.data;
    this.model = data["value"].model
    service.links = Object
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

  loader(query : SelectQuery, callback : (rows : any[], size: number) => void) : void {
    let link = "service/control/users/user/connections/connection/categories";
    const url = new URL(window.location.origin + "/" + link);
    url.searchParams.append("index", query.index + "")
    url.searchParams.append("limit", query.limit + "")

    fetch(url.toString())
      .then(response => response.json())
      .then(response => {
        callback(response.rows, response.size)
      })
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
