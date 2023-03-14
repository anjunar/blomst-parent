import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AppResolver implements Resolve<any> {
  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<any> {
    let urls : any[] = route.data["urls"];
    let regex = /\{(\w+)\}/g;
    let processed : string[] = [];

    for (const url of urls) {
      let entries = Object.entries(route.params);
      if (entries.length > 0) {
        for (const [key, value] of entries) {
          processed.push(url.value.replace(regex, (match : string, group : string) => {
            let newVar = route.paramMap.get(group);
            return newVar as string
          }));
        }
      } else {
        processed.push(url.value)
      }
    }

    let promises = [];
    for (const url of processed) {
      promises.push(secureFetch(url));
    }

    return Promise.all(promises);
  }
}
