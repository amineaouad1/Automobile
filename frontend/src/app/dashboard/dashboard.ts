import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InterventionService } from '../services/intervention';
import { InterventionDTO } from '../models/intervention.model';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.html',
  standalone: true,
  imports: [CommonModule]
})
export class DashboardComponent implements OnInit {
  interventions: InterventionDTO[] = [];

  stats = {
    recuesAujourdhui: 0,
    enDiagnostic: 0,
    enReparation: 0,
    terminees: 0,
    retards: 0
  };

  chargeMecaniciens: { nom: string, count: number }[] = [];

  constructor(private interventionService: InterventionService) {}

  ngOnInit(): void {
    this.loadDashboardData();
  }

  loadDashboardData(): void {
    this.interventionService.listerTous().subscribe({
      next: (data: InterventionDTO[]) => {
        this.interventions = data;
        this.calculateStats();
      },
      error: (err: any) => console.error('Erreur lors du chargement du dashboard', err)
    });
  }

  calculateStats(): void {
    const today = new Date();
    today.setHours(0, 0, 0, 0);

    let mecaniciensMap = new Map<string, number>();

    this.stats = { recuesAujourdhui: 0, enDiagnostic: 0, enReparation: 0, terminees: 0, retards: 0 };

    this.interventions.forEach(inv => {
      if (inv.dateDepot) {
        const dateDepot = new Date(inv.dateDepot);
        if (inv.statut === 'RECUE' && dateDepot >= today) {
          this.stats.recuesAujourdhui++;
        }
      }

      if (inv.statut === 'DIAGNOSTIC_EN_COURS') this.stats.enDiagnostic++;
      if (inv.statut === 'EN_REPARATION') this.stats.enReparation++;
      if (inv.statut === 'TERMINEE') this.stats.terminees++;

      if (inv.dateRestitutionPrevue && inv.statut !== 'RESTITUEE' && inv.statut !== 'ANNULEE') {
        const datePrevue = new Date(inv.dateRestitutionPrevue);
        if (datePrevue < new Date()) {
          this.stats.retards++;
        }
      }

      if (inv.mecanicien && (inv.statut === 'EN_REPARATION' || inv.statut === 'DEVIS_A_VALIDER')) {
        const nom = inv.mecanicien.nom;
        mecaniciensMap.set(nom, (mecaniciensMap.get(nom) || 0) + 1);
      }
    });

    this.chargeMecaniciens = Array.from(mecaniciensMap, ([nom, count]) => ({ nom, count }));
  }
}
