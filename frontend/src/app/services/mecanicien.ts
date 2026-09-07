import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MecanicienDTO } from '../models/mecanicien.model';

@Injectable({ providedIn: 'root' })
export class MecanicienService {
  private readonly apiUrl = 'http://localhost:8080/api/mecaniciens';

  constructor(private http: HttpClient) {}

  listerTous(): Observable<MecanicienDTO[]> {
    return this.http.get<MecanicienDTO[]>(this.apiUrl);
  }

  listerDisponibles(): Observable<MecanicienDTO[]> {
    return this.http.get<MecanicienDTO[]>(`${this.apiUrl}/disponibles`);
  }
}
