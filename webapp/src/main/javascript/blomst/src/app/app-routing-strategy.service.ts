import {ActivatedRouteSnapshot, BaseRouteReuseStrategy} from "@angular/router";
import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root',
})
export class AppRoutingStrategyService extends BaseRouteReuseStrategy {

  override shouldReuseRoute(future: ActivatedRouteSnapshot, curr: ActivatedRouteSnapshot): boolean {
    return false;
  }

}
