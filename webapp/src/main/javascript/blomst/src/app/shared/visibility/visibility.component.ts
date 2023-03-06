import {Component, HostListener, ViewEncapsulation} from '@angular/core';
import {AsWindowComponent, generateURL, SelectQuery} from "angular2-simplicity";

@Component({
  selector: 'app-visibility',
  templateUrl: 'visibility.component.html',
  styleUrls: ['visibility.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class VisibilityComponent {

  property! : string;
  clazz! : string;
  id! : string;
  model! : any;

  constructor(private window : AsWindowComponent) {}

  onLoad(event : {query : SelectQuery, callback : (rows : any[], size : number) => void}) {
    let link = "service/control/users/user/connections/connection/categories";
    const url = generateURL(link);
    url.searchParams.append("index", event.query.index + "")
    url.searchParams.append("limit", event.query.limit + "")
    url.searchParams.append("owner", this.id)

    secureFetch(url.toString())
      .then(response => response.json())
      .then(response => {
        event.callback(response.rows, response.size)
      })
  }

  onChange(model : any) {
    let url;
    if (this.property) {
      url = `service/shared/visibility/column?property=${this.property}&class=${this.clazz}&id=${this.id}`;
    } else {
      url = `service/shared/visibility/row?class=${this.clazz}&id=${this.id}`;
    }

    secureFetch(url, "POST", model)
  }

  @HostListener("click", ["$event"])
  onWindowClick(event : Event) {
    event.stopPropagation();
  }

  @HostListener("document:click")
  onDocumentClick() {
    this.window.close();
  }

}
