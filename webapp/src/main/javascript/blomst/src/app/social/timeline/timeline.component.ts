import {Component, ElementRef, Input, ViewChild, ViewEncapsulation} from '@angular/core';
import {
  AsInfiniteScrollComponent,
  InfinityQuery, Window,
  WindowManagerService
} from "angular2-simplicity";
import {LikesComponent} from "../../shared/likes/likes.component";
import {AppStartupService} from "../../app-startup.service";
import {OptionsComponent} from "./options/options.component";
import {TextPostComponent} from "./post/text-post/text-post.component";
import {ImagePostComponent} from "./post/image-post/image-post.component";
import {VideoPostComponent} from "./post/video-post/video-post.component";
import {CommentsComponent} from "./comments/comments.component";
import {AbstractPostForm} from "../../rest.classes";

@Component({
  selector: 'app-timeline',
  templateUrl: 'timeline.component.html',
  styleUrls: ['timeline.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class TimelineComponent {

  @Input() owner!: string

  @ViewChild(LikesComponent) likes!: LikesComponent
  @ViewChild(AsInfiniteScrollComponent) infinite! : AsInfiniteScrollComponent

  constructor(public service: AppStartupService, private windowManager: WindowManagerService, private elementRef : ElementRef) {
    this.owner = service.model.form.id;
  }

  postsLoader(event: { query: InfinityQuery, callback: (rows: any) => void }) {
    let link = "service/home/timeline";
    const url = new URL(window.location.origin + "/" + link);
    url.searchParams.append("index", event.query.index + "")
    url.searchParams.append("limit", event.query.limit + "")

    if (this.owner) {
      url.searchParams.append("owner", this.owner)
    }

    secureFetch(url.toString())
      .then(response => response.json())
      .then(response => {
        event.callback(response.rows || [])
      })
  }

  onLike(post: AbstractPostForm) {
    this.likes.model.likes = !this.likes.model.likes

    let method = "POST";
    let body = {form: post};

    secureFetch("service/home/timeline/post", method, body)
      .then(() => {
        this.likes.onFetchLikes();
      })
  }

  onVideoPostClick() {
    this.onPostClick("video", VideoPostComponent);
  }

  onImagePostClick() {
    this.onPostClick("image", ImagePostComponent);
  }

  onTextPostClick() {
    this.onPostClick("text", TextPostComponent);
  }

  onCommentClick(value : AbstractPostForm) {

    let clientRect = this.elementRef.nativeElement.getBoundingClientRect();

    secureFetch(`service/home/timeline/post/comments/comment/create?post=${value.id}`)
      .then(response => response.json())
      .then(response => {

        let options = {
          header : "Comments",
          dialog : true,
          width : "100%",
          minWidth : "380px",
          maxWidth : "640px",
          centerFn : (window : Window) => {
            return {
              top : `100px`,
              left : `calc(50% - ${window.element.offsetWidth / 2}px + ${clientRect.left / 2}px)`
            }
          }
        };

        let windowRef = this.windowManager.create(CommentsComponent, options);
        windowRef.instance.create = response;
        windowRef.instance.postId = value.id
      })
  }

  private onPostClick(value: string, component : any) {
    secureFetch(`service/home/timeline/post/create?type=${value}&source=${this.owner}`)
      .then(response => response.json())
      .then(response => {
        let windowRef = <any> this.windowManager.create(component, {
          header: "Post",
          dialog: true,
          width: "400px"
        });
        windowRef.instance.model = response;
      })
  }

  onOptionsClick(event : MouseEvent, id : string) {
    event.stopPropagation();
    let windowRef = this.windowManager.create(OptionsComponent, {
      header : "Options",
      width : "100px",
      top : event.clientY + "px",
      left : event.clientX - 100 + "px"
    });

    windowRef.instance.infinite = this.infinite
    windowRef.instance.id = id;
  }

}
