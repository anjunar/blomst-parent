import {Component, ViewEncapsulation} from '@angular/core';
import {AppNavigatorService} from "./navigator/app-navigator.service";
import {KeyValue} from "@angular/common";

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class AppComponent {

  open = true;
  name = "Blomst"

  constructor(private navigator : AppNavigatorService) {
    if (window.location.host.indexOf("poseidon") > -1) {
      this.name = "Poseidon"
    }
    let title = document.querySelector("title");
    if (title) {
      title.textContent = this.name;
    }
  }

  get service() {
    return btoa("service")
  }

  get links() {
    return this.navigator.links;
  }

  toBase64(value : any) {
    return btoa(value)
  }

  originalOrder = (a: KeyValue<number, string>, b: KeyValue<number, string>): number => {
    return 0;
  }

}
