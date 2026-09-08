export type TypeIntervention = 'DIAGNOSTIC' | 'REVISION' | 'REPARATION' | 'CONTROLE' | 'PNEUMATIQUES' | 'AUTRE';
export type Priorite = 'BASSE' | 'NORMALE' | 'HAUTE' | 'URGENTE';
export type StatutIntervention = 'RECUE' | 'DIAGNOSTIC_EN_COURS' | 'DEVIS_A_VALIDER' | 'EN_REPARATION' | 'TERMINEE' | 'RESTITUEE' | 'ANNULEE';

export interface MecanicienDTO {
  id: number;
  nom: string;
  specialite: string;
  disponible: boolean;
}

export interface CreateurDTO {
  id: number;
  nom: string;
  email: string;
}

export interface VehiculeResume {
  id: number;
  immatriculation: string;
  marque: string;
  modele: string;
  annee: number;
  kilometrage: number;
  clientFictif: string;
}

export interface InterventionDTO {
  id: number;
  vehicule: VehiculeResume;
  mecanicien: MecanicienDTO | null;
  createur: CreateurDTO;
  type: TypeIntervention;
  descriptionClient: string;
  diagnostic: string | null;
  statut: StatutIntervention;
  priorite: Priorite;
  coutEstime: number | null;
  dateDepot: string;
  dateRestitutionPrevue: string | null;
  dateCloture: string | null;
}

export interface CreerInterventionRequest {
  vehiculeId: number;
  type: TypeIntervention;
  descriptionClient: string;
  priorite?: Priorite;
}

export interface ChangerStatutRequest {
  nouveauStatut: StatutIntervention;
  commentaire?: string;
  coutEstime?: number;
  dateRestitutionPrevue?: string;
}

export interface AffecterMecanicienRequest {
  mecanicienId: number;
}

export interface DiagnostiquerRequest {
  diagnostic: string;
}

export interface HistoriqueInterventionDTO {
  id: number;
  auteurNom: string;
  ancienStatut: StatutIntervention | null;
  nouveauStatut: StatutIntervention;
  commentaire: string | null;
  date: string;
}
