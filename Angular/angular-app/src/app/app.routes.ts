import { provideRouter, Routes } from '@angular/router';
import { HomeComponent } from './home-component/home-component';
import { NotFoundComponent } from './not-found-component/not-found-component';
import { App } from './app';
import { bootstrapApplication } from '@angular/platform-browser';

export const routes: Routes = [
  { path: 'home', component: HomeComponent },
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
