import {AfterViewInit, Component, HostListener, ViewChild, ViewEncapsulation} from '@angular/core';
import {AsDialogComponent, AsScrollAreaComponent, AsWindowComponent, InfinityQuery} from "ng2-simplicity";

@Component({
  selector: 'app-likes-popup',
  templateUrl: 'likes-popup.component.html',
  styleUrls: ['likes-popup.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class LikesPopupComponent implements AfterViewInit {

  postId!: string;

  @ViewChild(AsScrollAreaComponent) scrollArea! : AsScrollAreaComponent

  constructor(private dialog : AsDialogComponent) {}

  usersLoader(event : {query : InfinityQuery, callback : (rows : any[]) => void}) {
    let link = "service/shared/likes";
    const url = new URL(window.location.origin + "/" + link);
    url.searchParams.append("post", this.postId)
    url.searchParams.append("index", event.query.index + "")
    url.searchParams.append("limit", event.query.limit + "")

    secureFetch(url.toString())
      .then(response => response.json())
      .then(response => {
        event.callback(response.rows || [])
      })
  }

  onUserClick() {
    this.dialog.close();
  }

  ngAfterViewInit(): void {
    setTimeout(() => {
      this.scrollArea.checkScrollBars()
    })
  }

}
