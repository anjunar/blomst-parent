import {ApplicationRef, Injectable} from '@angular/core';
import {Router} from "@angular/router";
import {Form, UserSelect} from "./rest.classes";
import {debounce} from "ng2-simplicity";

@Injectable({
  providedIn: 'root'
})
export class AppStartupService {

  model! :Form<UserSelect>

  constructor(private router : Router, private application : ApplicationRef) {
    let registry = new WeakMap();

    EventTarget.prototype.addEventListener = (function (_super) {
      return function (name, callback : (event : Event) => void) {
        let handler = (event : Event) => {
          callback(event)
          application.tick();
        };

        registry.set(callback, handler);

        // @ts-ignore
        return _super.apply(this, [name, handler])
      }
    })(EventTarget.prototype.addEventListener)

    EventTarget.prototype.removeEventListener = (function (_super) {
      return function (name, callback : (event : Event) => void) {

        let handler = registry.get(callback);

        // @ts-ignore
        return _super.apply(this, [name, handler])
      }
    })(EventTarget.prototype.removeEventListener)

    window.secureFetch = function (url: string, method: string = "GET", data?: any) {
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

        fetch(url, options)
          .then(response => {
            if (response.ok) {
              return response.json();
            } else {
              switch (response.status) {
                case 401: router.navigate(["/security/login"]); break;
                case 403: router.navigate(["/security/login"]); break;
                default: {}
              }
              throw {status : response.status, response : response.json()};
            }
          })
          .then((response) => {
            resolve(response);
          })
          .catch((response) => {
            reject(response);
          })
          .finally(() => {
            setTimeout(() => {
              application.tick()
            })
          })
      });
    }
  }

  init() {
    return secureFetch("service")
      .then(response => {
        this.model = response;
      })
  }

}
