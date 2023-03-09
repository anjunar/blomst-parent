import {Component, Input, ViewEncapsulation} from '@angular/core';
import {LikesComponent} from "../../../../shared/likes/likes.component";
import {CommentForm, Form} from "../../../../rest.classes";

@Component({
  selector: 'app-comment',
  templateUrl: 'comment.component.html',
  styleUrls: ['comment.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class CommentComponent {

  @Input() model! : CommentForm
  @Input() create! : Form<CommentForm>

  reply : boolean = false;

  onLike(comment: CommentForm, likes : LikesComponent) {
    comment.likes = ! comment.likes

    secureFetch(`service/home/timeline/post/comments/comment?id=${comment.id}`, "PUT", {form : comment})
      .then(() => {
        likes.onFetchLikes();
      })
  }

}
