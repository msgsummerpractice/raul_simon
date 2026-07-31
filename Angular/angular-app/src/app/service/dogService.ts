import { Observable } from 'rxjs';
import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

export interface DogImageResponse {
  message: string;
  status: string;
}

@Injectable({
  providedIn: 'root',
})
export class DogService {
  private readonly http = inject(HttpClient);
  private readonly dogApiUrl = 'https://dog.ceo/api/breeds/image/random';

  getRandomDogImage(): Observable<DogImageResponse> {
    return this.http.get<DogImageResponse>(this.dogApiUrl);
  }
}
