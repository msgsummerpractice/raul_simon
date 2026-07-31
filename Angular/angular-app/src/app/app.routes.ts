import { provideRouter, Routes } from '@angular/router';
import { HomeComponent } from './component/home-component/home-component';
import { NotFoundComponent } from './component/not-found-component/not-found-component';
import { App } from './app';
import { bootstrapApplication } from '@angular/platform-browser';

export const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: HomeComponent },
  {
    path: 'login',
    loadComponent: () =>
      import('./component/log-in-component/log-in-component').then((m) => m.LogInComponent),
  },
  { path: '**', component: NotFoundComponent },
];

bootstrapApplication(App, {
  providers: [provideRouter(routes)],
});
