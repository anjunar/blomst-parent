import {Component, ViewEncapsulation} from '@angular/core';
import {AppView} from "../../app.classes";
import {ActivatedRoute, Router} from "@angular/router";
import {AsMetaFormService, MetaFormGroup, WindowManagerService} from "ng2-simplicity";
import {Form, RegisterForm} from "../../rest.classes";
import {AppStartupService} from "../../app-startup.service";

@Component({
  selector: 'app-register',
  templateUrl: 'register.component.html',
  styleUrls: ['register.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class RegisterComponent extends AppView {

  model!: Form<RegisterForm>
  form!: MetaFormGroup

  constructor(
    private router: Router,
    route: ActivatedRoute,
    private service: AsMetaFormService,
    windowManager: WindowManagerService,
    private startUp : AppStartupService
  ) {
    super(route, windowManager);
    this.form = service.create(this.model.$schema.properties, this.model)
  }

  onSubmit() {
    let body = this.form.getValue()
    secureFetch("service/security/register", "POST", body)
      .then(() => {
        this.startUp.init().then(() => {
          this.router.navigate(["/timeline"])
        })
      })
  }

}
