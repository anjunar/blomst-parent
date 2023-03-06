import {Component, ElementRef, HostListener, OnDestroy, ViewEncapsulation} from '@angular/core';
import {AsWindowComponent} from "angular2-simplicity";
import {AppStartupService} from "../../app-startup.service";

@Component({
  selector: 'app-settings',
  templateUrl: 'settings.component.html',
  styleUrls: ['settings.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class SettingsComponent {

  handler! : () => void

  constructor(private window : AsWindowComponent, public startupService : AppStartupService) {}

  get service() {
    return btoa("service")
  }

  @HostListener("document:click")
  onDocumentClick() {
    this.window.close();
  }



}
