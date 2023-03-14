import {Component, HostListener, ViewEncapsulation} from '@angular/core';
import {AsInfiniteScrollComponent, AsWindowComponent} from "ng2-simplicity";
import {Post} from "../timeline.component";

@Component({
  selector: 'app-post-options',
  templateUrl: 'options.component.html',
  styleUrls: ['options.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class PostOptionsComponent {

  infinite! : AsInfiniteScrollComponent;
  post! : Post

  constructor(private window : AsWindowComponent) {}

  @HostListener("document:click")
  onDocumentClick() {
    this.window.close();
  }

  onDeleteClick() {
    secureFetch(`service/home/timeline/post?id=${this.post.id}`, "DELETE")
      .then(() => {
        this.infinite.delete(this.post)
      })
  }

}
