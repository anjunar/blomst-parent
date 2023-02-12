import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {Angular2SimplicityModule} from "angular2-simplicity";
import {AppNavigatorFormComponent} from './navigator/app-navigator-form/app-navigator-form.component';
import {AppNavigatorTableComponent} from './navigator/app-navigator-table/app-navigator-table.component'
import {RouteReuseStrategy} from "@angular/router";
import {AppRoutingStrategyService} from "./app-routing-strategy.service";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    AppComponent,
    AppNavigatorFormComponent,
    AppNavigatorTableComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        Angular2SimplicityModule,
        ReactiveFormsModule,
        FormsModule
    ],
  providers: [{
    provide: RouteReuseStrategy,
    useClass: AppRoutingStrategyService
  }],
  bootstrap: [AppComponent]
})
export class AppModule {
}
