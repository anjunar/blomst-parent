import {AfterViewInit, Component, ElementRef, Input, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {Post} from "../timeline.component";
import {PostOptionsComponent} from "../options/options.component";
import {
  AsInfiniteScrollComponent,
  AsMetaFormService,
  generateURL, InfinityQuery,
  MetaFormGroup,
  Window,
  WindowManagerService
} from "ng2-simplicity";
import {CommentsComponent} from "../comments/comments.component";
import {LikesComponent} from "../../../shared/likes/likes.component";
import {CommentForm, Form} from "../../../rest.classes";
import {AppStartupService} from "../../../app-startup.service";

@Component({
  selector: 'app-post',
  templateUrl: 'post.component.html',
  styleUrls: ['post.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class PostComponent implements AfterViewInit, OnInit {

  @Input() model! : Post
  @Input() infinite! : AsInfiniteScrollComponent

  formGroup! : MetaFormGroup
  play = false

  @ViewChild(LikesComponent) likes!: LikesComponent
  @ViewChild("video") video! : ElementRef<HTMLVideoElement>

  @ViewChild(AsInfiniteScrollComponent) scroll! : AsInfiniteScrollComponent

  constructor(
    private windowManager : WindowManagerService,
    private service : AsMetaFormService,
    public startUp : AppStartupService
  ) {
  }

  ngOnInit(): void {
    let url = generateURL("service/home/timeline/post/comments/comment/create");
    url.searchParams.append("post", this.model.id)
    secureFetch(url.toString())
      .then(response => {
        this.formGroup = this.service.create(response.$schema.properties, response)
      })
  }

  ngAfterViewInit(): void {
    if (this.video) {
      this.video.nativeElement.pause();
    }
  }

  onLoad(event: { query: InfinityQuery, callback: (rows: any) => void }) : void {
    let url = generateURL("service/home/timeline/post/comments");
    url.searchParams.append("index", event.query.index + "")
    url.searchParams.append("limit", event.query.limit + "")
    url.searchParams.append("post", this.model.id)

    secureFetch(url.toString())
      .then(response => {
        event.callback(response.rows || [])
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
           this.scroll.add(value.form)
        })
    }
  }

  onLike(post: Post) {
    this.likes.model.likes.active = !this.likes.model.likes.active

    let method = "POST";

    secureFetch("service/home/timeline/post", method, post)
      .then(() => {
        if (this.likes.model.likes.active) {
          this.likes.model.likes.count++
        } else {
          this.likes.model.likes.count--
        }
      })
  }

  onOptionsClick(event: MouseEvent, value: Post) {
    event.stopPropagation();
    let windowRef = this.windowManager.create(PostOptionsComponent, {
      header: "Options",
      width: "100px",
      top: event.clientY + "px",
      left: event.clientX - 100 + "px"
    });

    windowRef.instance.infinite = this.infinite
    windowRef.instance.post = value;
  }


}
