import {Component, ViewEncapsulation} from '@angular/core';
import {generateURL, InfinityQuery} from "angular2-simplicity";

interface Comment {
  text : string,
  likes : boolean
}

@Component({
  selector: 'app-comments',
  templateUrl: 'comments.component.html',
  styleUrls: ['comments.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class CommentsComponent {

  postId! : string
  commentId! : string

  typeToken!: { $implicit: Comment };

  onLoad(event: { query: InfinityQuery, callback: (rows: any) => void }) : void {
    let url = generateURL("service/home/timeline/post/comments");
    url.searchParams.append("index", event.query.index + "")
    url.searchParams.append("limit", event.query.limit + "")
    url.searchParams.append("post", this.postId)

    if (this.commentId) {
      url.searchParams.append("comment", this.commentId)
    }

    secureFetch(url.toString())
      .then(response => response.json())
      .then(response => {
        event.callback(response.rows)
      })
  }

}
