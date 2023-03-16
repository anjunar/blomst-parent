import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AppNavigatorResolver} from "./navigator/app-navigator.resolver";
import {AppResolver} from "./app.resolver";
import {TimelineComponent} from "./social/timeline/timeline.component";
import {RegisterComponent} from "./security/register/register.component";
import {LogoutComponent} from "./security/logout/logout.component";
import {LoginComponent} from "./security/login/login.component";
import {FormComponent} from "./navigator/form/form.component";
import {TableComponent} from "./navigator/table/table.component";
import {UserComponent} from "./control/user/user.component";
import {FriendsComponent} from "./control/user/friends/friends.component";

const routes: Routes = [
  {
    path : "friends",
    component : FriendsComponent
  },
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
      urls : [{
        name : "model",
        value : "service/security/register"
      }]
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
      urls : [
        {
          name : "model",
          value : "service/security/login"
        }
      ]
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
  },
  {
    path : ":user",
    component : UserComponent,
    resolve : {
      model : AppResolver
    },
    data : {
      urls : [{
        name : "user",
        value : "service/control/users/user/{user}"
      }]
    }
  }
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
