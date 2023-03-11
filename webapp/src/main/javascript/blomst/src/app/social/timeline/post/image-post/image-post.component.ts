import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {AsDialogComponent, AsMetaFormService, MetaFormGroup} from "ng2-simplicity";

@Component({
  selector: 'app-image-post',
  templateUrl: 'image-post.component.html',
  styleUrls: ['image-post.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class ImagePostComponent implements OnInit {

  model! : any
  form! : MetaFormGroup

  constructor(private service : AsMetaFormService, private window : AsDialogComponent) {}

  ngOnInit(): void {
    this.form = this.service.create(this.model.$schema.properties, this.model)
  }

  onSubmit() {
    secureFetch("service/home/timeline/post", "POST", this.form.getValue())
      .then(response => {
        this.window.close();
      })
  }

}
