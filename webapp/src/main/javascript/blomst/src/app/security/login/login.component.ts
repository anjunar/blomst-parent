import {Component, ViewEncapsulation} from '@angular/core';
import {AppView, Form} from "../../app.classes";
import {FormGroup} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {AsMetaFormService, MetaFormGroup, updateValues, WindowManagerService} from "angular2-simplicity";
import {AppStartupService} from "../../app-startup.service";

interface Login {
  email : string,
  password : string
}

@Component({
  selector: 'app-login',
  templateUrl: 'login.component.html',
  styleUrls: ['login.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class LoginComponent extends AppView {

  model! : Form<Login>
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
    secureFetch("service/security/login", "POST", body)
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
