import {Component, Input, OnInit, ViewEncapsulation} from '@angular/core';
import {AsViewportComponent, WindowManagerService} from "ng2-simplicity";
import {LikesPopupComponent} from "./likes-popup/likes-popup.component";
import {AbstractLikeableRestEntity} from "../../rest.classes";

@Component({
  selector: 'app-likes',
  templateUrl: 'likes.component.html',
  styleUrls: ['likes.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class LikesComponent implements OnInit {

  @Input() model! : AbstractLikeableRestEntity

  size = 0;

  constructor(private windowManager : WindowManagerService, private viewport : AsViewportComponent) {}

  ngOnInit(): void {
    this.onFetchLikes();
  }

  onShowUsers(event : MouseEvent) {
    event.stopPropagation()
    let options = {
      dialog : true,
      width : "320px",
      height : "200px",
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

    let model = this.model as any;

    switch (model["@type"]) {
      case "VideoPost" : {
        url.searchParams.append("post", this.model.id)
      } break;
      case "TextPost" : {
        url.searchParams.append("post", this.model.id)
      } break;
      case "ImagePost" : {
        url.searchParams.append("post", this.model.id)
      } break;
      case "Comment" : {
        url.searchParams.append("comment", this.model.id)
      } break;
    }

    return secureFetch(url.toString())
      .then(response => response.json())
      .then(response => {
        this.size = response.size
      })
  }
}
