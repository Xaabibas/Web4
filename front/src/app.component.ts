import { Component } from "@angular/core";
import { RouterOutlet, RouterLink, RouterLinkActive } from "@angular/router";
import { CommonModule } from "@angular/common";

@Component({
  selector: "app-root",
  standalone: true,
  imports: [
    CommonModule,
    RouterOutlet,
    RouterLink,
    RouterLinkActive
  ],
  template: `
      <nav style="padding: 10px; background-color: #f0f0f0;">
        <a routerLink="/" routerLinkActive="active" [routerLinkActiveOptions]="{exact: true}">Главная страница</a> |
        <a routerLink="/main" routerLinkActive="active">Страница с формой</a>
      </nav>
      <hr>

      <router-outlet></router-outlet>`,
  styleUrls: ["./styles.css"]
})
export class AppComponent {

}
