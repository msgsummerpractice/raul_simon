import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core/primitives/di';
import { Router } from '@angular/router';
import { throwError } from 'rxjs/internal/observable/throwError';
import { retry } from 'rxjs/internal/operators/retry';
import { catchError } from 'rxjs/operators';

export const errorInterceptor: HttpInterceptorFn = (req, next) => {
  const router = inject(Router);
  return next(req).pipe(
    catchError((error: HttpErrorResponse) => {
      return throwError(() => new Error(`An error occurred. ${error.message}`));
    }),
  );
};
