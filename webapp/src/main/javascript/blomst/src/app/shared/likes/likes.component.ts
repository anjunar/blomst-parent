import {Component, Input, OnInit, ViewEncapsulation} from '@angular/core';
import {AsViewportComponent, WindowManagerService} from "angular2-simplicity";
import {LikesPopupComponent} from "./likes-popup/likes-popup.component";

interface Likeable {
  id : string
  likes : boolean
}

@Component({
  selector: 'app-likes',
  templateUrl: 'likes.component.html',
  styleUrls: ['likes.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class LikesComponent implements OnInit {

  @Input() model! : Likeable

  size = 0;

  constructor(private windowManager : WindowManagerService, private viewport : AsViewportComponent) {}

  ngOnInit(): void {
    this.onFetchLikes();
  }

  onShowUsers(event : MouseEvent) {
    event.stopPropagation()
    let options = {
      dialog : true,
      width : 320,
      height : 200,
      header : "People who liked...",
    };
    let windowRef = this.windowManager.create(LikesPopupComponent, options);
    windowRef.instance.postId = this.model.id
  }

  onFetchLikes() {
    let link = "service/shared/likes";
    const url = new URL(window.location.origin + "/" + link);
    url.searchParams.append("index", "0")
    url.searchParams.append("limit", "0")
    url.searchParams.append("post", this.model.id)

    return fetch(url.toString())
      .then(response => response.json())
      .then(response => {
        this.size = response.size
      })
  }
}
