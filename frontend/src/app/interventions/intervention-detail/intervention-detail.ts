import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { InterventionService } from '../../services/intervention';
import { MecanicienService } from '../../services/mecanicien';
import { InterventionDTO, StatutIntervention } from '../../models/intervention.model';
import { MecanicienDTO } from '../../models/mecanicien.model';

const TRANSITIONS: Record<StatutIntervention, StatutIntervention[]> = {
  RECUE: ['DIAGNOSTIC_EN_COURS', 'ANNULEE'],
  DIAGNOSTIC_EN_COURS: ['DEVIS_A_VALIDER', 'ANNULEE'],
  DEVIS_A_VALIDER: ['EN_REPARATION', 'ANNULEE'],
  EN_REPARATION: ['TERMINEE', 'ANNULEE'],
  TERMINEE: ['RESTITUEE'],
  RESTITUEE: [],
  ANNULEE: [],
};

@Component({
  selector: 'app-intervention-detail',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './intervention-detail.html',
})
export class InterventionDetailComponent implements OnInit {
  intervention: InterventionDTO | null = null;
  mecaniciensDisponibles: MecanicienDTO[] = [];
  chargement = true;
  enCoursStatut = false;
  enCoursAffectation = false;
  erreur: string | null = null;

  statutForm: FormGroup;
  mecanicienForm: FormGroup;

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
    private fb: FormBuilder,
    private interventionService: InterventionService,
    private mecanicienService: MecanicienService,
  ) {
    this.statutForm = this.fb.group({
      nouveauStatut: ['', Validators.required],
      commentaire: [''],
      coutEstime: [null],
    });
    this.mecanicienForm = this.fb.group({
      mecanicienId: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.charger(id);
    this.mecanicienService.listerDisponibles().subscribe({
      next: (data) => (this.mecaniciensDisponibles = data),
    });
  }

  charger(id: number): void {
    this.chargement = true;
    this.interventionService.getById(id).subscribe({
      next: (data) => {
        this.intervention = data;
        this.chargement = false;
      },
      error: () => {
        this.erreur = 'Intervention introuvable.';
        this.chargement = false;
      },
    });
  }

  get transitionsPossibles(): StatutIntervention[] {
    if (!this.intervention) return [];
    return TRANSITIONS[this.intervention.statut] || [];
  }

  get afficherCoutEstime(): boolean {
    return this.statutForm.value.nouveauStatut === 'DEVIS_A_VALIDER';
  }

  changerStatut(): void {
    if (this.statutForm.invalid || !this.intervention) return;

    this.enCoursStatut = true;
    this.interventionService.changerStatut(this.intervention.id, this.statutForm.value).subscribe({
      next: (data) => {
        this.intervention = data;
        this.statutForm.reset();
        this.enCoursStatut = false;
      },
      error: (err) => {
        this.erreur = err?.error?.message || 'Erreur lors du changement de statut.';
        this.enCoursStatut = false;
      },
    });
  }

  affecterMecanicien(): void {
    if (this.mecanicienForm.invalid || !this.intervention) return;

    this.enCoursAffectation = true;
    const payload = { mecanicienId: Number(this.mecanicienForm.value.mecanicienId) };

    this.interventionService.affecterMecanicien(this.intervention.id, payload).subscribe({
      next: (data) => {
        this.intervention = data;
        this.mecanicienForm.reset();
        this.enCoursAffectation = false;
      },
      error: () => {
        this.erreur = "Erreur lors de l'affectation du mécanicien.";
        this.enCoursAffectation = false;
      },
    });
  }

  formaterStatut(statut: StatutIntervention): string {
    return this.labels[statut];
  }
}
