import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { InterventionService } from '../../services/intervention';
import { InterventionDTO, StatutIntervention } from '../../models/intervention.model';

@Component({
  selector: 'app-intervention-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './intervention-list.html',
})
export class InterventionListComponent implements OnInit {
  interventions: InterventionDTO[] = [];
  chargement = true;
  erreur: string | null = null;
  filtreStatut: StatutIntervention | 'TOUS' = 'TOUS';

  readonly statuts: StatutIntervention[] = [
    'RECUE',
    'DIAGNOSTIC_EN_COURS',
    'DEVIS_A_VALIDER',
    'EN_REPARATION',
    'TERMINEE',
    'RESTITUEE',
    'ANNULEE',
  ];

  private readonly labels: Record<StatutIntervention, string> = {
    RECUE: 'Reçue',
    DIAGNOSTIC_EN_COURS: 'Diagnostic en cours',
    DEVIS_A_VALIDER: 'Devis à valider',
    EN_REPARATION: 'En réparation',
    TERMINEE: 'Terminée',
    RESTITUEE: 'Restituée',
    ANNULEE: 'Annulée',
  };

  constructor(private interventionService: InterventionService) {}

  ngOnInit(): void {
    this.charger();
  }

  charger(): void {
    this.chargement = true;
    this.interventionService.listerTous().subscribe({
      next: (data) => {
        this.interventions = data.sort(
          (a, b) => new Date(b.dateDepot).getTime() - new Date(a.dateDepot).getTime(),
        );
        this.chargement = false;
      },
      error: () => {
        this.erreur = 'Impossible de charger les interventions.';
        this.chargement = false;
      },
    });
  }

  get interventionsFiltrees(): InterventionDTO[] {
    if (this.filtreStatut === 'TOUS') return this.interventions;
    return this.interventions.filter((i) => i.statut === this.filtreStatut);
  }

  formaterStatut(statut: StatutIntervention): string {
    return this.labels[statut];
  }
}
