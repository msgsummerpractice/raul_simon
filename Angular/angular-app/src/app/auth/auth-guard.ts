import { CanActivateFn, RedirectCommand, Router } from '@angular/router';
import { AuthService } from '../service/authService/auth-service';
import { inject } from '@angular/core';

export const authGuard: CanActivateFn = () => {
  const authService = inject(AuthService);
  const router = inject(Router);

  if (authService.isAuthentificated()) {
    return true;
  }

  return new RedirectCommand(router.parseUrl('login'));
};
