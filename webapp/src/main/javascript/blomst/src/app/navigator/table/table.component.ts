import {Component, OnDestroy} from '@angular/core';
import {TableQuery} from "ng2-simplicity";
import {ActivatedRoute, Router} from "@angular/router";
import {AppNavigatorService} from "../app-navigator.service";

@Component({
  selector: 'app-table',
  templateUrl: 'table.component.html',
  styleUrls: ['table.component.css']
})
export class TableComponent implements OnDestroy {

  link!: string;

  header! : string;

  constructor(private router : Router, private route: ActivatedRoute, protected service: AppNavigatorService) {
    route.queryParams.subscribe(params => {
      this.header = atob(params["link"])
    })
  }

  ngOnDestroy(): void {
    this.service.links = [];
  }

  ngOnInit(): void {
    let queryParams: any = this.route.queryParams;
    let queryParam = queryParams.value["link"];
    this.link = atob(queryParam);
  }

  loader(event : {query: TableQuery, callback: (rows: any[], size: number, schema: any) => void}): void {

    const url = new URL(window.location.origin + "/" + this.link);
    url.searchParams.append("index", event.query.index + "")
    url.searchParams.append("limit", event.query.limit + "")

    secureFetch(url.toString())
      .then(response => {
        event.callback(response.rows, response.size, response.$schema)
      })
  };

  onRowClick(model : any) {
    this.router.navigate(["/navigator/form"], {queryParams : {link : btoa(model.links.read.url)}})
  }

  onLoad(model : any) {
    this.service.links = model.$schema.links
  }

}
