import { Injectable } from '@angular/core';
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class AppStartupService {

  model! :any

  constructor(private router : Router) {
    window.secureFetch = function (url : string, method : string = "GET", data? : any) {
      return new Promise((resolve, reject) => {
        let options = {
          method: method
        };

        if (data) {
          Object.assign(options, {
            body: JSON.stringify(data),
            headers: {
              "Content-Type": "application/json"
            }
          })
        }

        fetch(url, options).then(response => {
          if (response.ok) {
            resolve(response);
          } else {
            switch (response.status) {
              case 401:
                router.navigate(["/security/login"])
                break;
              default:
                console.log('Some error occured');
                break;
            }

            reject(response);
          }
        })
      });
    }
  }

  init() {
    return secureFetch("service")
      .then(response => response.json())
      .then(response => {
        this.model = response;
      })
  }

}
