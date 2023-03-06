import {Component, ViewEncapsulation} from '@angular/core';
import {AppView, Form} from "../../app.classes";
import {ActivatedRoute, Router} from "@angular/router";
import {AsMetaFormService, MetaFormGroup, WindowManagerService} from "angular2-simplicity";

interface Register {
  email: string,
  password: string
  confirm: string
}

@Component({
  selector: 'app-register',
  templateUrl: 'register.component.html',
  styleUrls: ['register.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class RegisterComponent extends AppView {

  model!: Form<Register>
  form!: MetaFormGroup

  constructor(
    private router: Router,
    route: ActivatedRoute,
    private service: AsMetaFormService,
    windowManager: WindowManagerService
  ) {
    super(route, windowManager);
    this.form = service.create(this.model.$schema.properties, this.model)
  }

  onSubmit() {
    let body = this.form.getValue()
    secureFetch("service/security/register", "POST", body)
      .then(response => response.json())
      .then(response => {
        this.router.navigate(["/timeline"])
      })
  }

}
