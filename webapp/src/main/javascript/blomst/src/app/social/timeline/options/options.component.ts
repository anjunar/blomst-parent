import {Component, HostListener, ViewEncapsulation} from '@angular/core';
import {AsInfiniteScrollComponent, AsWindowComponent} from "ng2-simplicity";

@Component({
  selector: 'app-options',
  templateUrl: 'options.component.html',
  styleUrls: ['options.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class OptionsComponent {

  infinite! : AsInfiniteScrollComponent;
  id! : string

  constructor(private window : AsWindowComponent) {}

  @HostListener("document:click")
  onDocumentClick() {
    this.window.close();
  }

  onDeleteClick() {
    secureFetch(`service/home/timeline/post?id=${this.id}`, "DELETE")
      .then(() => {
        this.infinite.delete(this.id)
      })
  }

}
