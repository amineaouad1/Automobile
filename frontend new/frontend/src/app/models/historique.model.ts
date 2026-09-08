import { StatutIntervention } from './intervention.model';

export interface HistoriqueInterventionDTO {
  id: number;
  auteurNom: string;
  ancienStatut: StatutIntervention | null;
  nouveauStatut: StatutIntervention;
  commentaire: string | null;
  date: string;
}
