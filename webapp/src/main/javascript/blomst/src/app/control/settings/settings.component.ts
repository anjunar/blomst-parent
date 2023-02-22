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

  constructor(private window : AsWindowComponent) {}

  get service() {
    return btoa("service")
  }

  @HostListener("document:click")
  onDocumentClick() {
    this.window.close();
  }



}
