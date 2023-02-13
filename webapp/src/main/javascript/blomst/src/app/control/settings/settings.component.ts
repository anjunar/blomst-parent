import {Component, HostListener, ViewEncapsulation} from '@angular/core';
import {AsWindowComponent} from "angular2-simplicity";

@Component({
  selector: 'app-settings',
  templateUrl: 'settings.component.html',
  styleUrls: ['settings.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class SettingsComponent {

  constructor(private window : AsWindowComponent) {
    window.element.addEventListener("click", (event : Event) => {
      event.stopPropagation()
    })
  }

  get service() {
    return btoa("service")
  }

  @HostListener("window:click")
  onDocumentClick() {
    this.window.close();
  }
}
