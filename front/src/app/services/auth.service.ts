import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Router } from "@angular/router";
import { Observable } from "rxjs";
import { tap } from "rxjs/operators";
import { AuthRequest, AuthResponse } from "../interfaces/auth.interface";

@Injectable({ providedIn: "root" })
export class AuthService {
  private apiUrl = "http://localhost:8080";

  constructor(private http: HttpClient, private router: Router) {}

  public getToken(): string | null {
    return localStorage.getItem("jwt_token");
  }

  public isLoggedIn(): boolean {
    return !!this.getToken();
  }

  public register(request: AuthRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/register`, request).pipe(
          tap(response => {
            localStorage.setItem("jwt_token", response.jwt);
          })
        );
  }

  public login(request: AuthRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/auth`, request).pipe(
      tap(response => {
        localStorage.setItem("jwt_token", response.jwt);
      })
    );
  }

  public logout(): void {
    localStorage.removeItem("jwt_token");
    this.router.navigate(['/auth']);
  }
}
