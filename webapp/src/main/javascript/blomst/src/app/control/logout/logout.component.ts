import {Component, ViewEncapsulation} from '@angular/core';
import {Router} from "@angular/router";
import {AppStartupService} from "../../app-startup.service";

@Component({
  selector: 'app-logout',
  templateUrl: 'logout.component.html',
  styleUrls: ['logout.component.css'],
  encapsulation : ViewEncapsulation.None
})
export class LogoutComponent {

  constructor(private router : Router, private startUp : AppStartupService) {}

  onLogout() {
    fetch("service/security/logout", {method : "POST"})
      .then(response => response.json())
      .then(response => {
        this.startUp.init().then(() => {
          this.router.navigate(["/security/login"])
        })
      })
  }

}
