// import { Component } from '@angular/core';
// import { Router } from '@angular/router';
// import { MatAnchor } from '@angular/material/button';

// @Component({
//   selector: 'app-log-in-component',
//   imports: [MatAnchor],
//   templateUrl: './log-in-component.html',
//   styleUrl: './log-in-component.css',
// })
// export class LogInComponent {
//   constructor(private router: Router) {}

//   goHome() {
//     this.router.navigate(['/home']);
//   }
// }

import { Component, inject, signal } from '@angular/core';
import {
  FormBuilder,
  NonNullableFormBuilder,
  Validators,
  FormControl,
  FormGroup,
  ReactiveFormsModule,
} from '@angular/forms';
import { MatAnchor } from '@angular/material/button';
import { AuthService } from '../../service/authService/auth-service';
import { Router } from '@angular/router';

type LoginForm = {
  username: FormControl<string>;
  password: FormControl<string>;
};

@Component({
  selector: 'app-log-in-component',
  imports: [ReactiveFormsModule, MatAnchor],
  templateUrl: './log-in-component.html',
  styleUrl: './log-in-component.css',
})
export class LogInComponent {
  private readonly _formBuilder = inject(NonNullableFormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);
  protected readonly isSubmited = signal<boolean>(false);
  private usrnameForMfa = '';
  protected loginError = '';

  protected readonly loginFormGroup = this._formBuilder.group<LoginForm>({
    username: this._formBuilder.control('', Validators.required),
    password: this._formBuilder.control('', [Validators.required, Validators.minLength(6)]),
  });

  onFormSubmit(): void {
    this.loginError = '';
    if (this.loginFormGroup.invalid) {
      this.loginFormGroup.markAllAsTouched();
      return;
    }

    const { username, password } = this.loginFormGroup.getRawValue();

    this.authService.login(username, password).subscribe({
      next: () => {
        this.usrnameForMfa = username;
        this.isSubmited.set(true);
      },
      error: (err) => {
        this.isSubmited.set(false);
        if (err.status === 401) {
          this.loginError = 'Invalid username or password.';
        } else {
          this.loginError = 'Something went wrong. Please try again.';
        }
      },
    });
  }

  logout(): void {
    this.authService.logout();
  }

  isAuthenticated(): boolean {
    return this.authService.isAuthenticated();
  }

  get username() {
    return this.loginFormGroup.get('username');
  }

  get password() {
    return this.loginFormGroup.get('password');
  }

  verifyMfa(code: string): void {
    this.authService.verifyMfa(this.usrnameForMfa, code).subscribe({
      next: (response) => {
        this.authService.saveToken(response.accessToken);
        this.isSubmited.set(false);
        this.router.navigate(['/home']);
      },
    });
  }
}
