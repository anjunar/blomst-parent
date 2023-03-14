import {Component, HostListener} from '@angular/core';
import {AsInfiniteScrollComponent, AsWindowComponent, TableLike} from "ng2-simplicity";
import {CommentForm} from "../../../../../rest.classes";

@Component({
  selector: 'app-comment-options',
  templateUrl: 'options.component.html',
  styleUrls: ['options.component.css']
})
export class CommentOptionsComponent {

  list! : TableLike;
  comment! : CommentForm

  constructor(private window : AsWindowComponent) {}

  @HostListener("document:click")
  onDocumentClick() {
    this.window.close();
  }

  onDeleteClick() {
    secureFetch(`service/home/timeline/post/comments/comment?id=${this.comment.id}`, "DELETE")
      .then(() => {
        this.list.delete(this.comment)
      })
  }

}
