import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { MatAnchor } from '@angular/material/button';

@Component({
  selector: 'app-log-in-component',
  imports: [MatAnchor],
  templateUrl: './log-in-component.html',
  styleUrl: './log-in-component.css',
})
export class LogInComponent {
  constructor(private router: Router) {}

  goHome() {
    this.router.navigate(['/home']);
  }
}
