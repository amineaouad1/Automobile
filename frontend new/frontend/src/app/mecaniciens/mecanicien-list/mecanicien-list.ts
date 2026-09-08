import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { MecanicienService } from '../../services/mecanicien';
import { AuthService } from '../../services/auth';
import { MecanicienDTO } from '../../models/mecanicien.model';

@Component({
  selector: 'app-mecanicien-list',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './mecanicien-list.html',
})
export class MecanicienListComponent implements OnInit {
  mecaniciens: MecanicienDTO[] = [];
  chargement = true;
  erreur: string | null = null;
  enCoursId: number | null = null;

  constructor(
    private mecanicienService: MecanicienService,
    public authService: AuthService,
  ) {}

  ngOnInit(): void {
    this.charger();
  }

  charger(): void {
    this.chargement = true;
    this.mecanicienService.listerTous().subscribe({
      next: (data) => {
        this.mecaniciens = data;
        this.chargement = false;
      },
      error: () => {
        this.erreur = 'Impossible de charger les mécaniciens.';
        this.chargement = false;
      },
    });
  }

  toggleDisponibilite(m: MecanicienDTO): void {
    this.enCoursId = m.id;
    this.mecanicienService.changerDisponibilite(m.id, !m.disponible).subscribe({
      next: (updated) => {
        m.disponible = updated.disponible;
        this.enCoursId = null;
      },
      error: () => {
        this.erreur = 'Erreur lors de la mise à jour de la disponibilité.';
        this.enCoursId = null;
      },
    });
  }
}
