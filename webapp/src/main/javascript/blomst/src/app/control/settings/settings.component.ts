import {Component, ElementRef, HostListener, OnDestroy, ViewEncapsulation} from '@angular/core';
import {AsWindowComponent} from "angular2-simplicity";

@Component({
  selector: 'app-settings',
  templateUrl: 'settings.component.html',
  styleUrls: ['settings.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class SettingsComponent {

  handler! : () => void

  constructor(private window : AsWindowComponent) {
    window.element.addEventListener("click", (event : Event) => {
      event.stopPropagation()
    })

    this.handler = () => {
      this.onDocumentClick();
    }

    window.destroy.subscribe(() => {
      document.removeEventListener("click", this.handler)
    })

    document.addEventListener("click", this.handler)
  }

  get service() {
    return btoa("service")
  }

  onDocumentClick() {
    this.window.close();
  }



}
