import {Component, ViewEncapsulation} from '@angular/core';
import {generateURL, InfinityQuery, WindowManagerService} from "ng2-simplicity";
import {ConnectionStatus, UserConnectionForm, UserForm} from "../../rest.classes";

@Component({
  selector: 'app-users',
  templateUrl: 'users.component.html',
  styleUrls: ['users.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class UsersComponent {

  typeToken!: { $implicit: UserForm };

  constructor(private windowManager : WindowManagerService) {}

  usersLoader(event: { query: InfinityQuery, callback: (rows: any) => void }) {
    const url = generateURL("service/control/users")
    url.searchParams.append("index", event.query.index + "")
    url.searchParams.append("limit", event.query.limit + "")

    secureFetch(url.toString())
      .then(response => {
        event.callback(response.rows || [])
      })
  }

}
