import {Component, Input, ViewEncapsulation} from '@angular/core';
import {LikesComponent} from "../../../../shared/likes/likes.component";
import {CommentForm, Form} from "../../../../rest.classes";
import {TableLike, WindowManagerService, WindowOptions} from "ng2-simplicity";
import {CommentOptionsComponent} from "./options/options.component";

@Component({
  selector: 'app-comment',
  templateUrl: 'comment.component.html',
  styleUrls: ['comment.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class CommentComponent {

  @Input() model! : CommentForm
  @Input() list! : TableLike

  reply : boolean = false;

  constructor(private windowManager : WindowManagerService) {}

  onOptionsClick(event : MouseEvent) {
    event.stopPropagation();
    let options : WindowOptions = {
      header : "Options",
      top : event.clientY + "px",
      left : event.clientX + "px"
    };
    let windowRef = this.windowManager.create(CommentOptionsComponent, options);
    windowRef.instance.comment = this.model
    windowRef.instance.list = this.list
  }

  onLike(comment: CommentForm, likes : LikesComponent) {
    comment.likes.active = ! comment.likes.active

    secureFetch(`service/home/timeline/post/comments/comment?id=${comment.id}`, "PUT", comment)
      .then(() => {
        if (comment.likes.active) {
          likes.model.likes.count++
        } else {
          likes.model.likes.count--
        }

      })
  }

}
