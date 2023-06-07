import {Component, Input, ViewEncapsulation} from '@angular/core';
import {UserConnectionForm} from "../../../../../rest.classes";
import {WindowManagerService} from "ng2-simplicity";
import {FriendsOptionsComponent} from "./options/options.component";

@Component({
  selector: 'app-friend',
  templateUrl: 'friend.component.html',
  styleUrls: ['friend.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class FriendComponent {

  @Input() model!: UserConnectionForm

  constructor(private windowManager : WindowManagerService) {}

  onOptionsClick(event : MouseEvent) {
    event.stopPropagation();

    let options = {
      header : "Options",
      top : event.clientY + "px",
      left : event.clientX + "px"
    };

    let windowRef = this.windowManager.create(FriendsOptionsComponent, options);
    windowRef.instance.model = this.model;

  }
}
