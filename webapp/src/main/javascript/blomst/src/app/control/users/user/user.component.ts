import {Component, ViewChild, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {AppView} from "../../../app.classes";
import {
  AsLazyListComponent,
  AsMetaFormService,
  generateURL,
  ListQuery,
  MetaFormGroup,
  SelectQuery,
  WindowManagerService
} from "ng2-simplicity";
import {FormArray, FormGroup} from "@angular/forms";
import {AddressComponent} from "./details/address/address.component";
import * as mapbox from "mapbox-gl"
import {Form, JsonNodeUnion, JsonObject, UserConnectionForm, UserForm} from "../../../rest.classes";

(mapbox as typeof mapbox).accessToken = "pk.eyJ1IjoiYW5qdW5hciIsImEiOiJjbDFuczBnc20wd2g4M2NvMm1yMWp4aHpiIn0.1KbDOpN0gPaRq5MzS-N0Zw";

@Component({
  selector: 'app-user',
  templateUrl: 'user.component.html',
  styleUrls: ['user.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class UserComponent extends AppView {

  user!: Form<UserForm>;
  form!: MetaFormGroup;
  page = 0;

  constructor(route: ActivatedRoute, private model2Schema: AsMetaFormService, windowManager: WindowManagerService) {
    super(route, windowManager);
    this.form = model2Schema.create(this.user.$schema.properties, this.user)
  }

  onSubmit() {
    let user = this.form.getValue();
    secureFetch("service/control/users/user", "PUT", user)
  }

}
