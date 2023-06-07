import {Component, HostListener, ViewEncapsulation} from '@angular/core';
import {UserConnectionForm} from "../../../../../../rest.classes";
import {AsWindowComponent} from "ng2-simplicity";

@Component({
  selector: 'app-friend-options',
  templateUrl: 'options.component.html',
  styleUrls: ['options.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class FriendsOptionsComponent {

  model! : UserConnectionForm

  constructor(private window : AsWindowComponent) {}

  onDeleteClick() {
    secureFetch(`service/control/users/user/connections/connection?id=${this.model.id}`, "DELETE")
      .then(() => {
        this.window.close()
      })
  }

  @HostListener("document:click")
  onDocumentClick() {
    this.window.close()
  }

}
