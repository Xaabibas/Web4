import { bootstrapApplication } from '@angular/platform-browser';
import { Index } from './app/index/index';
import { Main } from './app/main/main';

bootstrapApplication(Index)
  .catch((err) => console.error(err));
