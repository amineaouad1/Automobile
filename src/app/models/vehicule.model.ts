export interface VehiculeDTO {
  id: number;
  immatriculation: string;
  marque: string;
  modele: string;
  annee: number;
  kilometrage: number;
  clientId: number;
}

export interface CreerVehiculeRequest {
  immatriculation: string;
  marque: string;
  modele: string;
  annee: number;
  kilometrage: number;
  clientId: number;
}
