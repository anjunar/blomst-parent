import {Component, EventEmitter, HostListener, Output, ViewEncapsulation} from '@angular/core';
import {AsWindowComponent, generateURL, SelectQuery} from "ng2-simplicity";
import {AppStartupService} from "../../../../../app-startup.service";

@Component({
  selector: 'app-address',
  templateUrl: 'address.component.html',
  styleUrls: ['address.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class AddressComponent {

  address : any

  @Output() submit = new EventEmitter<any>();

  constructor(private window : AsWindowComponent, private service : AppStartupService) {}

  onAddressLoad(event : {query : SelectQuery, callback : (rows : any[], size : number) => void}) {
    let url = generateURL("service/social/info/address/search");
    url.searchParams.append("value", event.query.value)

    secureFetch(url.toString())
      .then(response => {
        event.callback(response.rows, response.size)
      })
  }

  onCategoriesLoad(event : {query : SelectQuery, callback : (rows : any[], size : number) => void}) {
    let link = "service/control/users/user/connections/connection/categories";
    const url = new URL(window.location.origin + "/" + link);
    url.searchParams.append("index", event.query.index + "")
    url.searchParams.append("limit", event.query.limit + "")
    url.searchParams.append("owner", this.service.model.form.id)

    secureFetch(url.toString())
      .then(response => {
        event.callback(response.rows, response.size)
      })
  }


  onDocumentClick() {
    secureFetch("service/social/info/addresses/address", "POST", this.address)
      .then(() => {
        this.submit.emit(this.address);
        this.window.close();
      })
  }

}
