import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AppNavigatorResolver} from "./navigator/app-navigator.resolver";
import {AppResolver} from "./app.resolver";
import {TimelineComponent} from "./timeline/timeline.component";
import {RegisterComponent} from "./control/register/register.component";
import {LogoutComponent} from "./control/logout/logout.component";
import {LoginComponent} from "./control/login/login.component";
import {FormComponent} from "./navigator/form/form.component";
import {TableComponent} from "./navigator/table/table.component";

const routes: Routes = [
  {
    path : "timeline",
    component : TimelineComponent
  },
  {
    path : "security/register",
    component : RegisterComponent,
    resolve : {
      model : AppResolver
    },
    data : {
      url : "service/security/register"
    }
  },
  {
    path : "security/logout",
    component : LogoutComponent
  },
  {
    path : "security/login",
    component : LoginComponent,
    resolve : {
      model : AppResolver
    },
    data : {
      url : "service/security/login"
    }
  },
  {
    path: "navigator/form",
    runGuardsAndResolvers: 'always',
    component: FormComponent,
    resolve: {
      model: AppNavigatorResolver
    },
  },
  {
    path: "navigator/table",
    component: TableComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
