import { Component } from '@angular/core';
import { ThemeService } from './app/services/theme.service';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterModule],
  template: `
    <router-outlet></router-outlet>
  `,
})
export class AppComponent {
  constructor(public themeService: ThemeService) {}
}
