import {Component, Input, ViewChild, ViewEncapsulation} from '@angular/core';
import {
  AsInfiniteScrollComponent,
  InfinityQuery,
  WindowManagerService
} from "angular2-simplicity";
import {LikesComponent} from "../../shared/likes/likes.component";
import {AppStartupService} from "../../app-startup.service";
import {OptionsComponent} from "./options/options.component";
import {TextPostComponent} from "./text-post/text-post.component";
import {ImagePostComponent} from "./image-post/image-post.component";
import {VideoPostComponent} from "./video-post/video-post.component";

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

  constructor(public service: AppStartupService, private windowManager: WindowManagerService) {
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

  onLike(post: any) {
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

  private onPostClick(value: string, component : any) {
    secureFetch(`service/home/timeline/post/create?type=${value}&source=${this.owner}`)
      .then(response => response.json())
      .then(response => {
        let windowRef = <any> this.windowManager.create(component, {
          header: "Post",
          dialog: true,
          width: 400
        });
        windowRef.instance.model = response;
      })
  }

  onOptionsClick(event : MouseEvent, id : string) {
    event.stopPropagation();
    let windowRef = this.windowManager.create(OptionsComponent, {
      header : "Options",
      width : 100,
      top : event.clientY,
      left : event.clientX - 100
    });

    windowRef.instance.infinite = this.infinite
    windowRef.instance.id = id;
  }

}
