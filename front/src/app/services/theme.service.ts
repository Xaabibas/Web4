import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class ThemeService {
  private isDark = false;

  constructor() {
    const saved = localStorage.getItem('theme');
    saved === 'dark' ? this.enableDark() : this.enableLight();
  }

  enableDark() {
    this.isDark = true;
    document.body.classList.add('dark-theme');
    document.body.classList.remove('light-theme');
    localStorage.setItem('theme', 'dark');
  }

  enableLight() {
    this.isDark = false;
    document.body.classList.add('light-theme');
    document.body.classList.remove('dark-theme');
    localStorage.setItem('theme', 'light');
  }

  toggleTheme() {
    this.isDark ? this.enableLight() : this.enableDark();
  }

  isDarkTheme() {
    return this.isDark;
  }
}
