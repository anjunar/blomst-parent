import {Component, Input, OnInit, ViewEncapsulation} from '@angular/core';
import {ConnectionStatus, Form, UserConnectionForm, UserForm} from "../../../../rest.classes";
import {AppStartupService} from "../../../../app-startup.service";

@Component({
  selector: 'app-user-connection',
  templateUrl: 'connection.component.html',
  styleUrls: ['connection.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class UserConnectionComponent implements OnInit {

  @Input() user : UserForm

  connection : UserConnectionForm = {"@type": "UserConnection"}

  constructor(private startUp : AppStartupService) {}

  ngOnInit(): void {
    secureFetch(`service/control/users/user/connections/connection/resolve?from=${this.startUp.model.form.id}&to=${this.user.id}`)
      .then((response : Form<UserConnectionForm>) => {
        this.connection = response.form || {"@type": "UserConnection"};
      })
  }

  get isPrivate() {
    return this.user.id !== this.startUp.model.form.id;
  }

  onAddClick(user : UserForm, connection : UserConnectionForm) {
    secureFetch("service/control/users/user/connections/connection", "POST", connection)
      .then((response : UserConnectionForm) => {
        this.connection = response;
      })
  }

  onModifyClick(user : UserForm, connection : UserConnectionForm) {
    secureFetch("service/control/users/user/connections/connection", "PUT", connection)
      .then((response : UserConnectionForm) => {
        this.connection = response;
      })
  }

  onRemoveClick(user : UserForm, connection : UserConnectionForm) {
    secureFetch(`service/control/users/user/connections/connection?id=${connection.id}`, "DELETE", connection)
      .then((response : UserConnectionForm) => {
        this.connection = response;
      })
  }

}
