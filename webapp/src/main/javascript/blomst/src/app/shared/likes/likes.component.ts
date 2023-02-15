import {Component, Input, OnInit, ViewEncapsulation} from '@angular/core';

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

  ngOnInit(): void {
    let link = "service/home/timeline/post/likes";
    const url = new URL(window.location.origin + "/" + link);
    url.searchParams.append("index", "0")
    url.searchParams.append("limit", "0")
    url.searchParams.append("post", this.model.id)

    fetch(url.toString())
      .then(response => response.json())
      .then(response => {
        this.size = response.size
      })
  }

}
