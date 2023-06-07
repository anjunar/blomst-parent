import {Component, Input, ViewEncapsulation} from '@angular/core';
import {generateURL, InfinityQuery} from "ng2-simplicity";
import {AppStartupService} from "../../../../app-startup.service";
import {ConnectionStatus, Status, UserConnectionForm, UserForm, UserSelect} from "../../../../rest.classes";

export interface Twins {
  lhs : UserConnectionForm
  rhs : UserConnectionForm
}

@Component({
  selector: 'app-friends',
  templateUrl: 'friends.component.html',
  styleUrls: ['friends.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class FriendsComponent {

  @Input() user! : UserForm

  page = 0;

  typeToken!: { $implicit: UserConnectionForm };

  constructor(private startUp : AppStartupService) {}

  get isPrivate() {
    return this.user.id !== this.startUp.model.form.id
  }

  postsLoader(event: { query: InfinityQuery, callback: (rows: any) => void }, status : ConnectionStatus) {
    const url = generateURL("service/control/users/user/connections")
    url.searchParams.append("index", event.query.index + "")
    url.searchParams.append("limit", event.query.limit + "")
    url.searchParams.append("from", this.user.id)
    url.searchParams.append("status", status)

    secureFetch(url.toString())
      .then(response => {
        event.callback(response.rows || [])
      })
  }

}
