import { CanDeactivateFn } from '@angular/router';

export const confirmExitGuard: CanDeactivateFn<any> = (
  component,
  currentRoute,
  currentState,
  nextState,
) => {
  // built-in browser confirmation dialog returning a boolean
  return confirm('Are you sure you want to leave this page?');
};
