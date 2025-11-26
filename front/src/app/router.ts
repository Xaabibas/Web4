import { Routes } from "@angular/router";

import { MainComponent } from "./main.component";
import { IndexComponent } from "./index.component";

export const routes: Routes =[
    { path: "", component: IndexComponent },
    { path: "main", component: MainComponent },
    { path: "**", redirectTo: "", pathMatch: "full" }
];
