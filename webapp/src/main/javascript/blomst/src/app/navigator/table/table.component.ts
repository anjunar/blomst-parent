import { Component } from '@angular/core';
import {TableQuery} from "angular2-simplicity";
import {ActivatedRoute, Router} from "@angular/router";
import {AppNavigatorService} from "../app-navigator.service";

@Component({
  selector: 'app-table',
  templateUrl: 'table.component.html',
  styleUrls: ['table.component.css']
})
export class TableComponent {

  link!: string;

  header! : string;

  loader! : (query: TableQuery, callback: (rows: any[], size: number, schema: any) => void) => void;

  constructor(private router : Router, private route: ActivatedRoute, protected service: AppNavigatorService) {
    this.loader = (query: TableQuery, callback: (rows: any[], size: number, schema: any) => void): void => {

      const url = new URL(window.location.origin + "/" + this.link);
      url.searchParams.append("index", query.index + "")
      url.searchParams.append("limit", query.limit + "")

      fetch(url.toString())
        .then(response => response.json())
        .then(response => {
          callback(response.rows, response.size, response.$schema)
        })
    };

    route.queryParams.subscribe(params => {
      this.header = atob(params["link"])
    })
  }

  ngOnInit(): void {
    let queryParams: any = this.route.queryParams;
    let queryParam = queryParams.value["link"];
    this.link = atob(queryParam);
  }

  onRowClick(model : any) {
    this.router.navigate(["/navigator/form"], {queryParams : {link : btoa(model.links.read.url)}})
  }

  onLoad(model : any) {
    this.service.links = model.$schema.links
  }

}
