import { HttpClient } from '@angular/common/http';
import { inject, Injectable, signal } from '@angular/core';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthResponse, JwtPayload, User } from '../../auth/auth-model';
import { jwtDecode } from 'jwt-decode';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly http = inject(HttpClient);
  private readonly router = inject(Router);
  private currentUser = signal<User | null>(null);
  user = this.currentUser.asReadonly();
  private apiUrl =
    'https://raul-simon.politewave-44646487.germanywestcentral.azurecontainerapps.io/api/auth';

  constructor() {
    this.restoreUser();
  }

  login(username: string, password: string): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/login`, { username, password });
  }

  verifyMfa(username: string, code: string): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/mfa`, { username, mfaCode: code });
  }

  restoreUser(): void {
    const decoded = this.getDecodedToken();

    if (!decoded) {
      return;
    }

    this.currentUser.set({
      username: decoded.sub,
      roles: decoded.roles,
    });
  }

  saveToken(token: string): void {
    localStorage.setItem('token', token);
    this.restoreUser();
  }

  logout(): void {
    localStorage.removeItem('token');
    this.currentUser.set(null);
    this.router.navigate(['/login']);
  }

  isAuthenticated(): boolean {
    const decoded = this.getDecodedToken();

    return !!decoded?.exp && decoded.exp * 1000 > Date.now();
  }

  setUser(user: User): void {
    localStorage.setItem('user', JSON.stringify(user));
  }

  private getDecodedToken(): JwtPayload | null {
    const token = localStorage.getItem('token');

    if (!token) {
      return null;
    }

    try {
      return jwtDecode<JwtPayload>(token);
    } catch {
      return null;
    }
  }
}
