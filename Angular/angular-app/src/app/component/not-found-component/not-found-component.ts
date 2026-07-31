import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { MatAnchor } from '@angular/material/button';

@Component({
  selector: 'app-not-found-component',
  imports: [MatAnchor],
  templateUrl: './not-found-component.html',
  styleUrl: './not-found-component.css',
})
export class NotFoundComponent {
  constructor(private router: Router) {}

  goHome() {
    this.router.navigate(['/home']);
  }
}
