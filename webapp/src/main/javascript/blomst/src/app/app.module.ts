import {APP_INITIALIZER, NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {Angular2SimplicityModule} from "angular2-simplicity";
import {RouteReuseStrategy} from "@angular/router";
import {AppRoutingStrategyService} from "./app-routing-strategy.service";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {AppStartupService} from "./app-startup.service";
import { TimelineComponent } from './timeline/timeline.component';
import { LoginComponent } from './control/login/login.component';
import { LogoutComponent } from './control/logout/logout.component';
import { RegisterComponent } from './control/register/register.component';
import { SettingsComponent } from './control/settings/settings.component';
import { FormComponent } from './navigator/form/form.component';
import { TableComponent } from './navigator/table/table.component';
import { LikesComponent } from './shared/likes/likes.component';
import { HumanizePipe } from './shared/humanize.pipe';
import { LikesPopupComponent } from './shared/likes/likes-popup/likes-popup.component';

function appConfigFactory(service : AppStartupService) {
  return (): Promise<any> => {
    return service.init();
  }
}

@NgModule({
  declarations: [
    AppComponent,
    TimelineComponent,
    LoginComponent,
    LogoutComponent,
    RegisterComponent,
    SettingsComponent,
    FormComponent,
    TableComponent,
    LikesComponent,
    HumanizePipe,
    LikesPopupComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    Angular2SimplicityModule,
    ReactiveFormsModule,
    FormsModule
  ],
  providers: [
    {
      provide: RouteReuseStrategy,
      useClass: AppRoutingStrategyService
    },
    {
      provide: APP_INITIALIZER,
      useFactory: appConfigFactory,
      deps : [AppStartupService],
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
