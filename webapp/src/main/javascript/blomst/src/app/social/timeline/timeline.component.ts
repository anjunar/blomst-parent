import {Component, Input, ViewChild, ViewEncapsulation} from '@angular/core';
import {AsInfiniteScrollComponent, InfinityQuery, WindowManagerService} from "ng2-simplicity";
import {AppStartupService} from "../../app-startup.service";
import {TextPostComponent} from "./post/text-post/text-post.component";
import {ImagePostComponent} from "./post/image-post/image-post.component";
import {VideoPostComponent} from "./post/video-post/video-post.component";
import {AbstractPostForm, AbstractRestEntity, MediaType} from "../../rest.classes";

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

  @Input() owner!: AbstractRestEntity

  @ViewChild(AsInfiniteScrollComponent) infinite!: AsInfiniteScrollComponent

  typeToken!: { $implicit: Post };

  constructor(public service: AppStartupService, private windowManager: WindowManagerService) {
    this.owner = service.model.form;
  }

  postsLoader(event: { query: InfinityQuery, callback: (rows: any) => void }) {
    let link = "service/home/timeline";
    const url = new URL(window.location.origin + "/" + link);
    url.searchParams.append("index", event.query.index + "")
    url.searchParams.append("limit", event.query.limit + "")

    if (this.owner) {
      url.searchParams.append("owner", this.owner.id)
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
    secureFetch(`service/home/timeline/post/create?type=${value}&source=${this.owner.id}`)
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
