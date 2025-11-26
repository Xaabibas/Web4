import { Component, signal } from '@angular/core';
import { RouterOutlet} from "@angular/router";

@Component({
  selector: "index-page",
  imports: [RouterOutlet],
  templateUrl: './index.component.html',
  styleUrls: ['./styles/app.css', './styles/clock.css']
})
export class IndexComponent {

}
