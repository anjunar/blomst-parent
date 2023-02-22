import {Component, HostListener, ViewEncapsulation} from '@angular/core';
import {AsDialogComponent, AsWindowComponent, InfinityQuery} from "angular2-simplicity";

@Component({
  selector: 'app-likes-popup',
  templateUrl: 'likes-popup.component.html',
  styleUrls: ['likes-popup.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class LikesPopupComponent {

  postId!: string;

   constructor(private window : AsDialogComponent) {}

  usersLoader(event : {query : InfinityQuery, callback : (rows : any[]) => void}) {
    let link = "service/shared/likes";
    const url = new URL(window.location.origin + "/" + link);
    url.searchParams.append("post", this.postId)
    url.searchParams.append("index", event.query.index + "")
    url.searchParams.append("limit", event.query.limit + "")

    fetch(url.toString())
      .then(response => response.json())
      .then(response => {
        event.callback(response.rows || [])
      })
  }


}
