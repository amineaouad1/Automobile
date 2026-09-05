import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { VehiculeDTO, CreerVehiculeRequest } from '../models/vehicule.model';

@Injectable({
  providedIn: 'root',
})
export class VehiculeService {
  private readonly apiUrl = 'http://localhost:8080/api/vehicules';

  constructor(private http: HttpClient) {}

  listerTous(): Observable<VehiculeDTO[]> {
    return this.http.get<VehiculeDTO[]>(this.apiUrl);
  }

  getById(id: number): Observable<VehiculeDTO> {
    return this.http.get<VehiculeDTO>(`${this.apiUrl}/${id}`);
  }

  creer(request: CreerVehiculeRequest): Observable<VehiculeDTO> {
    return this.http.post<VehiculeDTO>(this.apiUrl, request);
  }
}
