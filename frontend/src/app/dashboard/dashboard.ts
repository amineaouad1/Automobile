import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { InterventionService } from '../services/intervention';
import { InterventionDTO, StatutIntervention } from '../models/intervention.model';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.html',
})
export class DashboardComponent implements OnInit {
  interventions: InterventionDTO[] = [];
  chargement = true;
  erreur: string | null = null;

  constructor(
    private interventionService: InterventionService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.chargerDonnees();
  }

  chargerDonnees(): void {
    this.chargement = true;
    this.interventionService.listerTous().subscribe({
      next: (data) => {
        this.interventions = data;
        this.chargement = false;
      },
      error: () => {
        this.erreur = 'Impossible de charger les interventions.';
        this.chargement = false;
      },
    });
  }

  compterParStatut(statut: StatutIntervention): number {
    return this.interventions.filter((i) => i.statut === statut).length;
  }

  get recuesAujourdhui(): number {
    const aujourdhui = new Date().toDateString();
    return this.interventions.filter(
      (i) => new Date(i.dateDepot).toDateString() === aujourdhui,
    ).length;
  }

  get totalActives(): number {
    return this.interventions.filter(
      (i) => i.statut !== 'RESTITUEE' && i.statut !== 'ANNULEE',
    ).length;
  }

  allerVersInterventions(): void {
    this.router.navigate(['/interventions']);
  }
}
