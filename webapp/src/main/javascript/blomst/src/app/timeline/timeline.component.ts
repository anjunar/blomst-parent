import {Component, ViewChild, ViewEncapsulation} from '@angular/core';
import {InfinityQuery} from "angular2-simplicity";
import * as moment from "moment";
import {LikesComponent} from "../shared/likes/likes.component";

@Component({
  selector: 'app-timeline',
  templateUrl: 'timeline.component.html',
  styleUrls: ['timeline.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class TimelineComponent {

  @ViewChild(LikesComponent) likes! : LikesComponent

  postsLoader(query : InfinityQuery, callback : (rows : any) => void) {
    let link = "service/home/timeline";
    const url = new URL(window.location.origin + "/" + link);
    url.searchParams.append("index", query.index + "")
    url.searchParams.append("limit", query.limit + "")

    fetch(url.toString())
      .then(response => response.json())
      .then(response => {
        callback(response.rows)
      })
 }

 onLike(post : any) {
   this.likes.model.likes = ! this.likes.model.likes

   let method = "POST";
   let headers = {"content-type" : "application/json"};
   let body = JSON.stringify({form : post});

   fetch("service/home/timeline/post", {method : method, headers : headers, body : body})
 }

 humanize(value : string) {
   let now = moment(new Date())
   let then = moment(value);
   let duration = moment.duration(now.diff(then));
   return duration.humanize();
 }

}
