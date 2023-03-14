import {Component, Input, OnInit, ViewEncapsulation} from '@angular/core';
import {AsViewportComponent, WindowManagerService} from "ng2-simplicity";
import {LikesPopupComponent} from "./likes-popup/likes-popup.component";
import {AbstractLikeableRestEntity} from "../../rest.classes";

@Component({
  selector: 'app-likes',
  templateUrl: 'likes.component.html',
  styleUrls: ['likes.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class LikesComponent {

  @Input() model! : AbstractLikeableRestEntity

  constructor(private windowManager : WindowManagerService) {}

  onShowUsers(event : MouseEvent) {
    event.stopPropagation()
    let options = {
      dialog : true,
      width : "320px",
      height : "200px",
      header : "People who liked...",
    };
    let windowRef = this.windowManager.create(LikesPopupComponent, options);
    windowRef.instance.postId = this.model.id
  }

}
