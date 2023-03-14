import {Injectable} from '@angular/core';
import {ActivatedRouteSnapshot, Resolve, RouterStateSnapshot} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AppNavigatorResolver implements Resolve<Promise<any>> {
  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Promise<any> {
    let link = atob(route.queryParams["link"]);
    return secureFetch(link)
  }
}
