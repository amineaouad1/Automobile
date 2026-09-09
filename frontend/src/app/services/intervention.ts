import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import {
  InterventionDTO,
  CreerInterventionRequest,
  ChangerStatutRequest,
  AffecterMecanicienRequest,
  DiagnostiquerRequest,
  HistoriqueInterventionDTO,
} from '../models/intervention.model';

@Injectable({ providedIn: 'root' })
export class InterventionService {
  private readonly apiUrl = 'http://localhost:8080/api/interventions';

  constructor(private http: HttpClient) {}

  listerTous(): Observable<InterventionDTO[]> {
    return this.http.get<InterventionDTO[]>(this.apiUrl);
  }

  getById(id: number): Observable<InterventionDTO> {
    return this.http.get<InterventionDTO>(`${this.apiUrl}/${id}`);
  }

  creer(request: CreerInterventionRequest): Observable<InterventionDTO> {
    return this.http.post<InterventionDTO>(this.apiUrl, request);
  }

  changerStatut(id: number, request: ChangerStatutRequest): Observable<InterventionDTO> {
    return this.http.patch<InterventionDTO>(`${this.apiUrl}/${id}/statut`, request);
  }

  affecterMecanicien(id: number, request: AffecterMecanicienRequest): Observable<InterventionDTO> {
    return this.http.patch<InterventionDTO>(`${this.apiUrl}/${id}/mecanicien`, request);
  }

  diagnostiquer(id: number, request: DiagnostiquerRequest): Observable<InterventionDTO> {
    return this.http.patch<InterventionDTO>(`${this.apiUrl}/${id}/diagnostic`, request);
  }

  getHistorique(id: number): Observable<HistoriqueInterventionDTO[]> {
    return this.http.get<HistoriqueInterventionDTO[]>(`${this.apiUrl}/${id}/historique`);
  }
}
