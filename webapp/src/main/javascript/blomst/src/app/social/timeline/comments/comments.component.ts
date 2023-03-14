import {Component, Input, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {
  AsExpandableListComponent,
  AsInfiniteScrollComponent,
  AsMetaFormService, ExpandableQuery,
  generateURL,
  InfinityQuery, ListQuery,
  MetaFormGroup
} from "ng2-simplicity";
import {CommentForm, Form} from "../../../rest.classes";
import {Post} from "../timeline.component";
import {AppStartupService} from "../../../app-startup.service";

@Component({
  selector: 'app-comments',
  templateUrl: 'comments.component.html',
  styleUrls: ['comments.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class CommentsComponent implements OnInit {

  @Input() post! : Post
  @Input() comment! : CommentForm

  formGroup! : MetaFormGroup

  @ViewChild(AsExpandableListComponent) list! : AsExpandableListComponent

  typeToken!: { $implicit: CommentForm };

  constructor(private service : AsMetaFormService, public startUp : AppStartupService) {}

  self() {
    return this;
  }

  ngOnInit(): void {
    let url = generateURL("service/home/timeline/post/comments/comment/create");
    if (this.post) {
      url.searchParams.append("post", this.post.id)
    }
    if (this.comment) {
      url.searchParams.append("parent", this.comment.id)
    }

    secureFetch(url.toString())
      .then(response => {
        this.formGroup = this.service.create(response.$schema.properties, response)
      })
  }

  onLoad(event: { query: ExpandableQuery, callback: (rows: any, size : number) => void }) : void {
    let url = generateURL("service/home/timeline/post/comments");
    url.searchParams.append("index", event.query.index + "")
    url.searchParams.append("limit", event.query.limit + "")
    if (this.post) {
      url.searchParams.append("post", this.post.id)
    }

    if (this.comment) {
      url.searchParams.append("parent", this.comment.id)
    }

    secureFetch(url.toString())
      .then(response => {
        event.callback(response.rows || [], response.size)
      })
  }

  onKeyUp(event : KeyboardEvent) {
    if (event.key === "Enter") {
      let value : Form<CommentForm> = this.formGroup.getValue();

      let form = this.formGroup.get("form");
      if (form) {
        let text = form.get("text");
        if (text) {
          text.setValue("")
        }
      }

      secureFetch("service/home/timeline/post/comments/comment", "POST", value.form)
        .then(response => {
          Object.assign(value.form, response)
          this.list.add(value.form)
        })
    }
  }

}
