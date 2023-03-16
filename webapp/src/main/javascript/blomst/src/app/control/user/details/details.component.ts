import {Component, Input, ViewChild, ViewEncapsulation} from '@angular/core';
import {Form, JsonNodeUnion, JsonObject, UserForm} from "../../../rest.classes";
import {AppView} from "../../../app.classes";
import {FormArray, FormGroup} from "@angular/forms";
import {
  AsLazyListComponent,
  AsMetaFormService,
  generateURL,
  ListQuery,
  MetaFormGroup,
  SelectQuery,
  WindowManagerService
} from "ng2-simplicity";
import * as mapbox from "mapbox-gl";
import {AddressComponent} from "../address/address.component";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-details',
  templateUrl: 'details.component.html',
  styleUrls: ['details.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class DetailsComponent extends AppView {

  @Input() form!: MetaFormGroup;
  @Input() user!: Form<UserForm>;
  addressSchema! : any

  @ViewChild(AsLazyListComponent) addresses!: AsLazyListComponent

  constructor(route: ActivatedRoute, windowManager: WindowManagerService, private model2Schema: AsMetaFormService) {
    super(route, windowManager);
  }

  get properties(): { [p: string]: JsonNodeUnion } {
    let property = this.user.$schema.properties["form"] as JsonObject;
    return property.properties;
  }

  get emails(): FormArray {
    let control: FormGroup = this.form.controls["form"] as FormGroup;
    return control.controls["emails"] as FormArray
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
      .then(response => {
        event.callback(response.rows, response.size);
      })
  }

  onAddressesLoad(event: { query: ListQuery, callback: (rows: any[], size: number) => void }) {
    let url = generateURL(`service/social/info/addresses?owner=${this.user.form.id}`)
    url.searchParams.append("index", event.query.index + "")
    url.searchParams.append("limit", event.query.limit + "")

    secureFetch(url.toString())
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
    let windowRef = this.windowManager.create(AddressComponent, {header: "Address", width: "400px"});
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
