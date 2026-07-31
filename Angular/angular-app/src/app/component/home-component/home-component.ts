import { Component, signal, inject } from '@angular/core';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { forkJoin } from 'rxjs';
import { DogService } from '../../service/dogService';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../service/authService/auth-service';
// import { Auth } from '../auth/auth';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-home-component',
  standalone: true,
  imports: [MatToolbarModule, MatButtonModule, MatIconModule, CommonModule, RouterLink],
  templateUrl: './home-component.html',
})
export class HomeComponent {
  protected readonly title = signal('angular-app');
  private readonly dogService = inject(DogService);
  private readonly authService = inject(AuthService);
  dogImages = signal<string[]>([]);

  loadDogImages(): void {
    const requests = [
      this.dogService.getRandomDogImage(),
      this.dogService.getRandomDogImage(),
      this.dogService.getRandomDogImage(),
    ];

    forkJoin(requests).subscribe({
      next: (results) => {
        const images = results.map((dog) => dog.message);
        this.dogImages.set(images);
      },
      error: (error) => console.error('Error loading dog images:', error),
    });
  }

  login(): void {
    this.authService.login();
  }

  logout(): void {
    this.authService.logout();
  }
}
