import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { VehiculeService } from '../../services/vehicule';
import { VehiculeDTO } from '../../models/vehicule.model';

@Component({
  selector: 'app-vehicule-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './vehicule-list.html',
})
export class VehiculeListComponent implements OnInit {
  vehicules: VehiculeDTO[] = [];
  chargement = true;
  erreur: string | null = null;

  constructor(
    private vehiculeService: VehiculeService,
    private router: Router,
  ) {}

  ngOnInit(): void {
    this.chargerVehicules();
  }

  chargerVehicules(): void {
    this.chargement = true;
    this.vehiculeService.listerTous().subscribe({
      next: (data) => {
        this.vehicules = data;
        this.chargement = false;
      },
      error: (err) => {
        this.erreur = 'Impossible de charger les véhicules.';
        this.chargement = false;
        console.error(err);
      },
    });
  }

  allerVersCreation(): void {
    this.router.navigate(['/vehicules/nouveau']);
  }
}
