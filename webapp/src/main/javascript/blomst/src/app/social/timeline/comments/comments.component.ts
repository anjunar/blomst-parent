import {Component, Input, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {
  AsInfiniteScrollComponent,
  AsMetaFormService,
  generateURL,
  InfinityQuery,
  MetaFormGroup
} from "angular2-simplicity";
import {CommentForm, Form} from "../../../rest.classes";

@Component({
  selector: 'app-comments',
  templateUrl: 'comments.component.html',
  styleUrls: ['comments.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class CommentsComponent implements OnInit {

  @Input() postId! : string
  @Input() commentId! : string
  @Input() create!: Form<CommentForm>;
  formGroup! : MetaFormGroup

  @ViewChild(AsInfiniteScrollComponent) scroll! : AsInfiniteScrollComponent

  typeToken!: { $implicit: CommentForm };

  constructor(private service : AsMetaFormService) {
  }

  ngOnInit(): void {
    this.formGroup = this.service.create(this.create.$schema.properties, this.create)
    this.create = JSON.parse(JSON.stringify(this.create));
  }

  onLoad(event: { query: InfinityQuery, callback: (rows: any) => void }) : void {
    let url = generateURL("service/home/timeline/post/comments");
    url.searchParams.append("index", event.query.index + "")
    url.searchParams.append("limit", event.query.limit + "")
    if (this.postId) {
      url.searchParams.append("post", this.postId)
    }

    if (this.commentId) {
      url.searchParams.append("parent", this.commentId)
    }

    secureFetch(url.toString())
      .then(response => response.json())
      .then(response => {
        event.callback(response.rows || [])
      })
  }

  onKeyUp(event : KeyboardEvent) {
    if (event.key === "Enter") {
      let value = this.formGroup.getValue();
      secureFetch("service/home/timeline/post/comments/comment", "POST", value)
        .then(response => response.json())
        .then(response => {
          this.scroll.add(value.form)
        })
    }
  }

}
