import { Routes } from "@angular/router";

import { MainComponent } from "./main.component";
import { LoginComponent } from "./auth/login/login.component";
import { RegisterComponent } from "./auth/register/register.component";
import { AuthGuard } from "./guards/auth.guard";

export const routes: Routes = [
    { path: "", redirectTo: "/login", pathMatch: "full" },
    { path: "login", component: LoginComponent },
    { path: "register", component: RegisterComponent },
    {
      path: "main",
      component: MainComponent,
      canActivate: [AuthGuard]
    },
    { path: "**", redirectTo: "/auth" }
];
