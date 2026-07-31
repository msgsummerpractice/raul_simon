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

import { Component, inject } from '@angular/core';
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
  email: FormControl<string>;
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

  protected readonly loginFormGroup = this._formBuilder.group<LoginForm>({
    email: this._formBuilder.control('', [Validators.required, Validators.email]),
    password: this._formBuilder.control('', [Validators.required, Validators.minLength(6)]),
  });

  onFormSubmit(): void {
    if (this.loginFormGroup.valid) {
      console.log('getRawValue():', this.loginFormGroup.getRawValue());
      console.log('value:', this.loginFormGroup.value);
      this.authService.login();
      this.router.navigate(['/home']);
    }
  }
}
