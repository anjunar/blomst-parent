import {AfterViewInit, Component, ViewChild, ViewEncapsulation} from '@angular/core';
import {AsDialogComponent, AsScrollAreaComponent, InfinityQuery} from "ng2-simplicity";
import {Post} from "../../../social/timeline/timeline.component";
import {UserSelect} from "../../../rest.classes";

@Component({
  selector: 'app-likes-popup',
  templateUrl: 'likes-popup.component.html',
  styleUrls: ['likes-popup.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class LikesPopupComponent implements AfterViewInit {

  postId!: string;

  @ViewChild(AsScrollAreaComponent) scrollArea!: AsScrollAreaComponent

  typeToken!: { $implicit: UserSelect };

  constructor(private dialog: AsDialogComponent) {}

  usersLoader(event: { query: InfinityQuery, callback: (rows: any[]) => void }) {
    let link = "service/shared/likes";
    const url = new URL(window.location.origin + "/" + link);
    url.searchParams.append("post", this.postId)
    url.searchParams.append("index", event.query.index + "")
    url.searchParams.append("limit", event.query.limit + "")

    secureFetch(url.toString())
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
