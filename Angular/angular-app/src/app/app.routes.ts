import { provideRouter, Routes } from '@angular/router';
import { HomeComponent } from './home-component/home-component';
import { NotFoundComponent } from './not-found-component/not-found-component';
import { App } from './app';
import { bootstrapApplication } from '@angular/platform-browser';
import { authGuard } from './auth/auth-guard';
import { confirmExitGuard } from './auth/confirm-exit-guard';

export const routes: Routes = [
  {
    path: 'home',
    component: HomeComponent,
    canActivate: [authGuard],
    canDeactivate: [confirmExitGuard],
  },
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  {
    path: 'login',
    loadComponent: () =>
      import('./log-in-component/log-in-component').then((m) => m.LogInComponent),
  },
  { path: '**', component: NotFoundComponent },
];

bootstrapApplication(App, {
  providers: [provideRouter(routes)],
});
