import {Component, ViewEncapsulation} from '@angular/core';
import {Form} from "../../app.classes";
import {FormGroup} from "@angular/forms";
import {ActivatedRoute, Router} from "@angular/router";
import {AsMetaFormService} from "angular2-simplicity";

interface Register {
  email : string,
  password : string
  confirm : string
}

@Component({
  selector: 'app-register',
  templateUrl: 'register.component.html',
  styleUrls: ['register.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class RegisterComponent {

  model! : Form<Register>
  form! : FormGroup

  constructor(private router : Router, private route : ActivatedRoute, private service : AsMetaFormService) {
    let data :any = route.data;
    this.model = data["value"].model
    this.form = service.create(this.model.$schema.properties, this.model)
  }

  onSubmit() {
    let body = JSON.stringify(this.form.getRawValue())
    let headers = {"content-type" : "application/json"};
    fetch("service/security/register", {body :  body, method: "POST", headers : headers})
      .then(response => response.json())
      .then(response => {
          this.router.navigate(["/timeline"])
      })
  }

}
