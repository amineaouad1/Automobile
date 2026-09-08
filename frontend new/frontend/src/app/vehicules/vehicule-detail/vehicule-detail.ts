import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { VehiculeService } from '../../services/vehicule';
import { InterventionService } from '../../services/intervention';
import { VehiculeDTO } from '../../models/vehicule.model';
import { InterventionDTO, StatutIntervention } from '../../models/intervention.model';

@Component({
  selector: 'app-vehicule-detail',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './vehicule-detail.html',
})
export class VehiculeDetailComponent implements OnInit {
  vehicule: VehiculeDTO | null = null;
  interventions: InterventionDTO[] = [];
  chargement = true;
  erreur: string | null = null;

  private readonly labels: Record<StatutIntervention, string> = {
    RECUE: 'Reçue',
    DIAGNOSTIC_EN_COURS: 'Diagnostic en cours',
    DEVIS_A_VALIDER: 'Devis à valider',
    EN_REPARATION: 'En réparation',
    TERMINEE: 'Terminée',
    RESTITUEE: 'Restituée',
    ANNULEE: 'Annulée',
  };

  constructor(
    private route: ActivatedRoute,
    private vehiculeService: VehiculeService,
    private interventionService: InterventionService,
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));

    this.vehiculeService.getById(id).subscribe({
      next: (data) => {
        this.vehicule = data;
        this.chargement = false;
      },
      error: () => {
        this.erreur = 'Véhicule introuvable.';
        this.chargement = false;
      },
    });

    this.interventionService.listerTous().subscribe({
      next: (data) => {
        this.interventions = data
          .filter((i) => i.vehicule.id === id)
          .sort((a, b) => new Date(b.dateDepot).getTime() - new Date(a.dateDepot).getTime());
      },
    });
  }

  formaterStatut(statut: StatutIntervention): string {
    return this.labels[statut];
  }
}
