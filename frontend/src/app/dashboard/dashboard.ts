import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { InterventionService } from '../services/intervention';
import { InterventionDTO, StatutIntervention } from '../models/intervention.model';

interface StatutBar {
  statut: StatutIntervention;
  label: string;
  count: number;
  pourcentage: number;
  couleur: string;
}

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

  private readonly config: { statut: StatutIntervention; label: string; couleur: string }[] = [
    { statut: 'RECUE', label: 'Reçue', couleur: '#1565c0' },
    { statut: 'DIAGNOSTIC_EN_COURS', label: 'Diagnostic', couleur: '#e65100' },
    { statut: 'DEVIS_A_VALIDER', label: 'Devis', couleur: '#ad1457' },
    { statut: 'EN_REPARATION', label: 'Réparation', couleur: '#5e35b1' },
    { statut: 'TERMINEE', label: 'Terminée', couleur: '#2e7d32' },
    { statut: 'RESTITUEE', label: 'Restituée', couleur: '#455a64' },
  ];

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

  get repartitionParStatut(): StatutBar[] {
    const max = Math.max(1, ...this.config.map((c) => this.compterParStatut(c.statut)));
    return this.config.map((c) => {
      const count = this.compterParStatut(c.statut);
      return {
        statut: c.statut,
        label: c.label,
        count,
        pourcentage: (count / max) * 100,
        couleur: c.couleur,
      };
    });
  }

  allerVersInterventions(): void {
    this.router.navigate(['/interventions']);
  }
}
