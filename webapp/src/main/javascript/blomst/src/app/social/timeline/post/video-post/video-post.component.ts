import {Component, ViewEncapsulation} from '@angular/core';
import {AsDialogComponent, AsMetaFormService, MetaFormGroup} from "ng2-simplicity";

@Component({
  selector: 'app-video-post',
  templateUrl: 'video-post.component.html',
  styleUrls: ['video-post.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class VideoPostComponent {

  model! : any
  form! : MetaFormGroup

  progress : number = 0;

  constructor(private service : AsMetaFormService, private window : AsDialogComponent) {}

  ngOnInit(): void {
    this.form = this.service.create(this.model.$schema.properties, this.model)
  }

  onSubmit() {
    let request = new XMLHttpRequest();

    request.open("POST", "service/home/timeline/post");
    request.setRequestHeader("Content-Type", "application/json");

    request.upload.addEventListener('progress', (event) => {
      this.progress = Math.round((event.loaded / event.total) * 100);
    })

    request.addEventListener("loadend", () => {
      this.window.close();
    })

    request.send(JSON.stringify(this.form.getValue()));
  }

}
