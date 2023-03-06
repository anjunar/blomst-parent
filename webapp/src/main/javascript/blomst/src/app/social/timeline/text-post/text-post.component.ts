import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {AsDialogComponent, AsMetaFormService, MetaFormGroup} from "angular2-simplicity";

@Component({
  selector: 'app-text-post',
  templateUrl: 'text-post.component.html',
  styleUrls: ['text-post.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class TextPostComponent implements OnInit {

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
