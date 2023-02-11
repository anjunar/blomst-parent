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

  constructor(private navigator : AppNavigatorService) {}

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
