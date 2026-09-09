export interface MecanicienDTO {
  id: number;
  nom: string;
  specialite: string;
  disponible: boolean;
}

export interface CreerMecanicienRequest {
  nom: string;
  specialite: string;
}
