import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AppNavigatorFormComponent} from "./navigator/app-navigator-form/app-navigator-form.component";
import {AppNavigatorResolver} from "./navigator/app-navigator.resolver";
import {AppNavigatorTableComponent} from "./navigator/app-navigator-table/app-navigator-table.component";

const routes: Routes = [
  {
    path: "form",
    component: AppNavigatorFormComponent,
    resolve: {
      model: AppNavigatorResolver
    }
  },
  {
    path: "table",
    component: AppNavigatorTableComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
