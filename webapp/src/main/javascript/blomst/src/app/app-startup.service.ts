import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AppStartupService {

  model! :any

  init() {
    return fetch("service")
      .then(response => response.json())
      .then(response => {
        this.model = response;
      })
  }

}
