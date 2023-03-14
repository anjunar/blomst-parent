import {Component, ElementRef, Input, ViewChild, ViewEncapsulation} from '@angular/core';
import {
  AsInfiniteScrollComponent,
  InfinityQuery, Window,
  WindowManagerService
} from "ng2-simplicity";
import {LikesComponent} from "../../shared/likes/likes.component";
import {AppStartupService} from "../../app-startup.service";
import {PostOptionsComponent} from "./options/options.component";
import {TextPostComponent} from "./post/text-post/text-post.component";
import {ImagePostComponent} from "./post/image-post/image-post.component";
import {VideoPostComponent} from "./post/video-post/video-post.component";
import {CommentsComponent} from "./comments/comments.component";
import {AbstractPostForm, AbstractPostFormUnion, MediaType} from "../../rest.classes";

export interface Post extends AbstractPostForm {
  image: MediaType;
  video: MediaType;
}

@Component({
  selector: 'app-timeline',
  templateUrl: 'timeline.component.html',
  styleUrls: ['timeline.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class TimelineComponent {

  @Input() owner!: string

  @ViewChild(AsInfiniteScrollComponent) infinite!: AsInfiniteScrollComponent

  typeToken!: { $implicit: Post };

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
      .then(response => {
        event.callback(response.rows || [])
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

  private onPostClick(value: string, component: any) {
    secureFetch(`service/home/timeline/post/create?type=${value}&source=${this.owner}`)
      .then(response => {
        let windowRef = <any>this.windowManager.create(component, {
          header: "Post",
          dialog: true,
          width: "400px"
        });
        windowRef.instance.model = response;
      })
  }

}
