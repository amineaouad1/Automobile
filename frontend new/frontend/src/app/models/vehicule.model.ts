export interface VehiculeDTO {
  id: number;
  immatriculation: string;
  marque: string;
  modele: string;
  annee: number;
  kilometrage: number;
  clientFictif: string;
}

export interface CreerVehiculeRequest {
  immatriculation: string;
  marque: string;
  modele: string;
  annee: number;
  kilometrage: number;
  clientFictif: string;
}
