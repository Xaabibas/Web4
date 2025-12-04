import { Component } from "@angular/core";
import { RouterOutlet, RouterLink, RouterLinkActive } from "@angular/router";
import { CommonModule } from "@angular/common";

@Component({
  selector: "app-root",
  standalone: true,
  imports: [
    CommonModule,
    RouterOutlet
  ],
  template: `<router-outlet></router-outlet>`,
  styleUrls: ["./styles.css"]
})
export class AppComponent {

}
