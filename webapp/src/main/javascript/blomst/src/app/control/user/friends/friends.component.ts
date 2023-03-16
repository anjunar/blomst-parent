import {Component, ViewEncapsulation} from '@angular/core';
import {generateURL, InfinityQuery} from "ng2-simplicity";
import {AppStartupService} from "../../../app-startup.service";
import {UserConnectionForm} from "../../../rest.classes";

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

  typeToken!: { $implicit: Twins };

  constructor(public startUp: AppStartupService) {}

  postsLoader(event: { query: InfinityQuery, callback: (rows: any) => void }) {
    const url = generateURL("service/control/users/user/connections")
    url.searchParams.append("index", event.query.index + "")
    url.searchParams.append("limit", event.query.limit + "")
    url.searchParams.append("from", this.startUp.model.form.id)

    secureFetch(url.toString())
      .then(response => {
        let rows = response.rows || [];
        let twins : Twins[] = [];
        for (let i = 0; i < rows.length; i = i + 2) {
          twins.push({
            lhs : rows[i],
            rhs : rows[i + 1]
          })
        }
        event.callback(twins)
      })
  }

}
