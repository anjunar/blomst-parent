import {Component, ViewChild, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {AppView, Form} from "../../app.classes";
import {
  AsLazyListComponent,
  AsMetaFormService,
  generateURL,
  ListQuery,
  MetaFormGroup,
  SelectQuery,
  WindowManagerService
} from "angular2-simplicity";
import {FormArray, FormGroup} from "@angular/forms";
import {AddressComponent} from "./address/address.component";
import * as mapbox from "mapbox-gl"

(mapbox as typeof mapbox).accessToken = "pk.eyJ1IjoiYW5qdW5hciIsImEiOiJjbDFuczBnc20wd2g4M2NvMm1yMWp4aHpiIn0.1KbDOpN0gPaRq5MzS-N0Zw";

interface Node {
  type: string
}

interface UserSchema {
  nickName: Node,
  firstName: Node,
  lastName: Node
  birthDate: Node,
  emails: Node,
  roles: Node
}

interface Image {
  name: string
  lastModified: string
  data: string,
  cropped?: Image
}

interface Email {
  value: string
  confirmed: boolean
}

interface Language {
  locale: string
  name: string
}

interface Role {
  name: string
  description: string
}

interface User {

  "@type": string

  id: string;

  nickName: string

  firstName: string

  lastName: string

  birthDate: string

  picture: Image

  emails: Email[]

  enabled: boolean

  language: Language

  roles: Role[]
}

@Component({
  selector: 'app-user',
  templateUrl: 'user.component.html',
  styleUrls: ['user.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class UserComponent extends AppView {

  user!: Form<User>;
  form!: MetaFormGroup;
  addressSchema! : any
  page = 0;

  @ViewChild(AsLazyListComponent) addresses!: AsLazyListComponent

  get properties(): UserSchema {
    return this.user.$schema.properties.form.properties;
  }

  get emails(): FormArray {
    let control: FormGroup = this.form.controls["form"] as FormGroup;
    return control.controls["emails"] as FormArray
  }

  constructor(route: ActivatedRoute, private model2Schema: AsMetaFormService, windowManager: WindowManagerService) {
    super(route, windowManager);
    this.form = model2Schema.create(this.user.$schema.properties, this.user)
  }

  onSubmit() {
    let user = this.form.getValue();
    secureFetch("service/control/users/user", "PUT", user)
  }

  onRolesLoad(event: { query: SelectQuery, callback: (rows: any[], size: number) => void }) {
    let url = generateURL("service/control/roles");
    url.searchParams.append("index", event.query.index + "")
    url.searchParams.append("limit", event.query.limit + "")

    secureFetch(url.toString())
      .then(response => response.json())
      .then(response => {
        event.callback(response.rows, response.size);
      })
  }

  onAddressesLoad(event: { query: ListQuery, callback: (rows: any[], size: number) => void }) {
    let url = generateURL(`service/social/info/addresses?owner=${this.user.form.id}`)
    url.searchParams.append("index", event.query.index + "")
    url.searchParams.append("limit", event.query.limit + "")

    secureFetch(url.toString())
      .then(response => response.json())
      .then(response => {
        event.callback(response.rows, response.size);
        this.addressSchema = response.$schema;

        if (response.rows) {
          for (const row of response.rows) {
            setTimeout(() => {
              let map = new mapbox.Map({
                container: row.id,
                style: 'mapbox://styles/mapbox/dark-v11',
                center: [row.x, row.y],
                zoom: 14
              });

              const marker = new mapbox.Marker()
                .setLngLat([row.x, row.y])
                .addTo(map);
            })
          }
        }
      })
  }

  removeEmail(value: any) {
    let indexOf = this.emails.controls.indexOf(value);
    this.emails.removeAt(indexOf);
    this.form.markAsDirty();
  }

  addEmail(schema: any) {
    let form = this.model2Schema.schema2Form(schema.items, {});
    this.emails.push(form)
    this.form.markAsDirty();
  }

  addAddress(event: Event) {
    event.stopPropagation();
    let windowRef = this.windowManager.create(AddressComponent, {header: "Address", width: 400});
    windowRef.instance.submit.subscribe(() => {
      this.addresses.load();
    })
  }

  removeAddress(value: any) {
    let url = generateURL("service/social/info/addresses/address");
    url.searchParams.append("id", value.id);

    secureFetch(url.toString(), "DELETE")
  }

}
