import {Component, ViewEncapsulation} from '@angular/core';
import {AppView} from "../../app.classes";
import {ActivatedRoute, Router} from "@angular/router";
import {AsMetaFormService, MetaFormGroup, updateValues, WindowManagerService} from "ng2-simplicity";
import {AppStartupService} from "../../app-startup.service";
import {Form, LoginForm} from "../../rest.classes";

@Component({
  selector: 'app-login',
  templateUrl: 'login.component.html',
  styleUrls: ['login.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class LoginComponent extends AppView {

  model! : Form<LoginForm>
  form! : MetaFormGroup

  constructor(
    private router : Router, route : ActivatedRoute,
    private service : AsMetaFormService,
    private startUp : AppStartupService,
    windowManager : WindowManagerService
  ) {
    super(route, windowManager);
    this.form = service.create(this.model.$schema.properties, this.model)
  }

  onSubmit() {
    let body = this.form.getValue();
    secureFetch("service/security/login", "POST", body.form)
      .then(response => {
        this.startUp.init().then(() => {
          this.router.navigate(["/timeline"])
        })
      })
      .catch(response => {
        this.form.setErrors({
          invalid : true
        })
        return response
      })

  }

}
