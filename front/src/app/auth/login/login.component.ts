import { Component, OnInit } from "@angular/core";
import { CommonModule } from "@angular/common";
import { Router, RouterOutlet, RouterLink, RouterLinkActive } from "@angular/router";
import { FormControl, FormGroup, Validators, ReactiveFormsModule } from "@angular/forms";

import { MatInputModule } from "@angular/material/input";
import { MatButtonModule } from "@angular/material/button";
import { MatFormFieldModule } from "@angular/material/form-field";

import { AuthService } from "../../services/auth.service";
import { AuthRequest } from "../../interfaces/auth.interface";

@Component({
  selector: "app-auth",
  standalone: true,
  imports: [
      ReactiveFormsModule,
      CommonModule,
      MatFormFieldModule,
      MatInputModule,
      MatButtonModule,
      RouterLink,
      RouterLinkActive
    ],
  templateUrl: "login.component.html",
  styleUrl: "../auth.css"
})
export class LoginComponent implements OnInit {
  authForm!: FormGroup;
  errorMessage: string | null = null;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    if (this.authService.isLoggedIn()) {
      this.router.navigate(["/main"]);
      return;
    }

    this.authForm = new FormGroup({
      username: new FormControl("", [Validators.required, Validators.minLength(3)]),
      password: new FormControl("", [Validators.required, Validators.minLength(3)])
    });
  }

  onLogin(): void {
    this.errorMessage = null;

    if (this.authForm.invalid) {
      this.authForm.markAllAsTouched();
      return;
    }

    const request: AuthRequest = this.authForm.value;

    this.authService.login(request).subscribe({
      next: () => {
        this.router.navigate(["/main"]);
      },
      error: (error) => {
        this.errorMessage = error.message;
      }
    });
  }
}
