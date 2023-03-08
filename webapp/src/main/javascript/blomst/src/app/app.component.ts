import {
  AfterContentChecked, AfterViewChecked,
  AfterViewInit,
  ApplicationRef, ChangeDetectorRef,
  Component,
  ComponentFactoryResolver,
  Injector,
  OnInit,
  ViewChild,
  ViewEncapsulation
} from '@angular/core';
import {AppNavigatorService} from "./navigator/app-navigator.service";
import {KeyValue} from "@angular/common";
import {AppStartupService} from "./app-startup.service";
import {
  AppMain, AsScrollAreaComponent,
  AsViewportComponent,
  ContextManagerService,
  WindowManagerService,
  WindowOptions
} from "angular2-simplicity";
import {SettingsComponent} from "./control/settings/settings.component";
import {Router} from "@angular/router";
import {ChangeDetection} from "@angular/cli/lib/config/workspace-schema";

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class AppComponent extends AppMain implements AfterViewInit {

  open = true;
  name = "Blomst"

  @ViewChild(AsViewportComponent) viewPort! : AsViewportComponent
  @ViewChild(AsScrollAreaComponent) scroll! : AsScrollAreaComponent

  constructor(
    private router : Router,
    private navigator: AppNavigatorService,
    private startUp : AppStartupService,
    private changeDetectionRef : ChangeDetectorRef,
    windowManager : WindowManagerService,
    contextManager : ContextManagerService,
    injector : Injector,
    application : ApplicationRef)
  {
    super(windowManager, contextManager, injector, application)

    if (window.location.host.indexOf("poseidon") > -1) {
      this.name = "Poseidon"
    }
    let title = document.querySelector("title");
    if (title) {
      title.textContent = this.name;
    }

    if (! this.isLoggedIn) {
      router.navigate(["/security/login"])
    }

    let matchMedia = window.matchMedia("(max-width: 800px)");
    this.open = ! matchMedia.matches;
  }

  get isLoggedIn() {
    return this.startUp.model.$schema.links.logout
  }

  get viewport(): AsViewportComponent {
    return this.viewPort;
  }

  ngAfterViewInit(): void {
    this.initialize();
  }

  get image() {
    return this.startUp.model.form.picture?.id;
  }

  get links() {
    return this.navigator.links;
  }

  toBase64(value: any) {
    return btoa(value)
  }

  onUserSettings(event : Event) {
    event.stopPropagation();

    let options : WindowOptions = {
      header : "Setting",
      top : 1,
      right : 10,
      draggable : false,
      resizeable : false,
      width : 300,
      height : 200
    };

    let windowRef = this.windowManager.create(SettingsComponent, options);
    return false;
  }

  originalOrder = (a: KeyValue<number, string>, b: KeyValue<number, string>): number => {
    return 0;
  }

}
