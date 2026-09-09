import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MecanicienDTO, CreerMecanicienRequest } from '../models/mecanicien.model';

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

  creer(request: CreerMecanicienRequest): Observable<MecanicienDTO> {
    return this.http.post<MecanicienDTO>(this.apiUrl, request);
  }

  changerDisponibilite(id: number, disponible: boolean): Observable<MecanicienDTO> {
    return this.http.patch<MecanicienDTO>(`${this.apiUrl}/${id}/disponibilite`, { disponible });
  }
}
