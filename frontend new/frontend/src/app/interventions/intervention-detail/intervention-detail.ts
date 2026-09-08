import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { InterventionService } from '../../services/intervention';
import { MecanicienService } from '../../services/mecanicien';
import { AuthService } from '../../services/auth';
import { InterventionDTO, StatutIntervention, HistoriqueInterventionDTO } from '../../models/intervention.model';
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
  historique: HistoriqueInterventionDTO[] = [];
  chargement = true;
  enCoursStatut = false;
  enCoursAffectation = false;
  enCoursDiagnostic = false;
  erreur: string | null = null;

  statutForm: FormGroup;
  mecanicienForm: FormGroup;
  diagnosticForm: FormGroup;

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
    public authService: AuthService,
  ) {
    this.statutForm = this.fb.group({
      nouveauStatut: ['', Validators.required],
      commentaire: [''],
      coutEstime: [null],
      dateRestitutionPrevue: [null],
    });
    this.mecanicienForm = this.fb.group({
      mecanicienId: ['', Validators.required],
    });
    this.diagnosticForm = this.fb.group({
      diagnostic: ['', Validators.required],
    });
  }

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.charger(id);
    this.chargerHistorique(id);
    this.mecanicienService.listerDisponibles().subscribe({
      next: (data) => (this.mecaniciensDisponibles = data),
    });
  }

  charger(id: number): void {
    this.chargement = true;
    this.interventionService.getById(id).subscribe({
      next: (data) => {
        this.intervention = data;
        this.diagnosticForm.patchValue({ diagnostic: data.diagnostic ?? '' });
        this.chargement = false;
      },
      error: () => {
        this.erreur = 'Intervention introuvable.';
        this.chargement = false;
      },
    });
  }

  chargerHistorique(id: number): void {
    this.interventionService.getHistorique(id).subscribe({
      next: (data) => (this.historique = data),
    });
  }

  get transitionsPossibles(): StatutIntervention[] {
    if (!this.intervention) return [];
    const transitions = TRANSITIONS[this.intervention.statut] || [];
    // La restitution est réservée au responsable atelier (RG-AUTO-07)
    if (!this.authService.isManager()) {
      return transitions.filter((s) => s !== 'RESTITUEE');
    }
    return transitions;
  }

  get afficherCoutEstime(): boolean {
    return this.statutForm.value.nouveauStatut === 'DEVIS_A_VALIDER';
  }

  get diagnosticModifiable(): boolean {
    if (!this.intervention) return false;
    return this.intervention.statut !== 'RESTITUEE' && this.intervention.statut !== 'ANNULEE';
  }

  enregistrerDiagnostic(): void {
    if (this.diagnosticForm.invalid || !this.intervention) return;

    this.enCoursDiagnostic = true;
    this.interventionService.diagnostiquer(this.intervention.id, this.diagnosticForm.value).subscribe({
      next: (data) => {
        this.intervention = data;
        this.enCoursDiagnostic = false;
      },
      error: (err) => {
        this.erreur = err?.error?.message || "Erreur lors de l'enregistrement du diagnostic.";
        this.enCoursDiagnostic = false;
      },
    });
  }

  changerStatut(): void {
    if (this.statutForm.invalid || !this.intervention) return;

    this.enCoursStatut = true;
    const payload = { ...this.statutForm.value };
    if (payload.dateRestitutionPrevue) {
      // L'input HTML type="date" renvoie "yyyy-MM-dd" ; le backend attend un LocalDateTime ISO.
      payload.dateRestitutionPrevue = `${payload.dateRestitutionPrevue}T00:00:00`;
    } else {
      delete payload.dateRestitutionPrevue;
    }

    this.interventionService.changerStatut(this.intervention.id, payload).subscribe({
      next: (data) => {
        this.intervention = data;
        this.statutForm.reset();
        this.enCoursStatut = false;
        this.chargerHistorique(data.id);
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

  formaterStatut(statut: StatutIntervention | null): string {
    return statut ? this.labels[statut] : '—';
  }
}
