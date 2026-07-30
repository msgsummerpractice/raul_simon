import { Component, signal, inject } from '@angular/core';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { forkJoin } from 'rxjs';
import { DogService } from './dogService';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [MatToolbarModule, MatButtonModule, MatIconModule, CommonModule],
  templateUrl: './app.html',
})
export class App {
  protected readonly title = signal('angular-app');
  private dogService = inject(DogService);
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
}
