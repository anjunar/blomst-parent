import {Component, ViewEncapsulation} from '@angular/core';
import {Form} from "../../app.classes";
import {FormGroup} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {AsMetaFormService} from "angular2-simplicity";
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
export class LoginComponent {

  model! : Form<Login>
  form! : FormGroup

  constructor(private router : Router, private route : ActivatedRoute, private service : AsMetaFormService,private startUp : AppStartupService) {
    let data :any = route.data;
    this.model = data["value"].model
    this.form = service.create(this.model.$schema.properties, this.model)
  }

  onSubmit() {
    let body = JSON.stringify(this.form.getRawValue())
    let headers = {"content-type" : "application/json"};
    fetch("service/security/login", {body :  body, method: "POST", headers : headers})
      .then(response => {
        if (response.ok) {
          return response.json()
        }
        throw Error();
      })
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
