import {Component, Input, ViewEncapsulation} from '@angular/core';
import {Twins} from "../friends.component";

@Component({
  selector: 'app-friend',
  templateUrl: 'friend.component.html',
  styleUrls: ['friend.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class FriendComponent {

  @Input() model!: Twins

}
