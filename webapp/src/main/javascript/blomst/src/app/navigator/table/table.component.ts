import {Component, ViewEncapsulation} from '@angular/core';
import {generateURL, TableQuery} from "ng2-simplicity";
import {ActivatedRoute, Router} from "@angular/router";
import {KeyValue} from "@angular/common";

@Component({
  selector: 'app-table',
  templateUrl: 'table.component.html',
  styleUrls: ['table.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class TableComponent {

  links: any;

  link!: string;

  header!: string;

  constructor(private router: Router, private route: ActivatedRoute) {
    route.queryParams.subscribe(params => {
      this.header = atob(params["link"])
    })
  }

  ngOnInit(): void {
    let queryParams: any = this.route.queryParams;
    let queryParam = queryParams.value["link"];
    this.link = atob(queryParam);
  }

  loader(event: { query: TableQuery, callback: (rows: any[], size: number, schema: any) => void }): void {

    const url = generateURL(this.link);
    url.searchParams.append("index", event.query.index + "")
    url.searchParams.append("limit", event.query.limit + "")

    secureFetch(url.toString())
      .then(response => {
        event.callback(response.rows, response.size, response.$schema)
      })
  };

  onRowClick(model: any) {
    this.router.navigate(["/navigator/form"], {queryParams: {link: btoa(model.links.read.url)}})
  }

  onLoad(model: any) {
    this.links = model.$schema.links
  }

  toBase64(value: any) {
    return btoa(value)
  }

  originalOrder = (a: KeyValue<number, string>, b: KeyValue<number, string>): number => {
    return 0;
  }


}
