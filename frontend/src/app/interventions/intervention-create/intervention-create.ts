import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { InterventionService } from '../../services/intervention';
import { VehiculeService } from '../../services/vehicule';
import { VehiculeDTO } from '../../models/vehicule.model';

@Component({
  selector: 'app-intervention-create',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './intervention-create.html',
})
export class InterventionCreateComponent implements OnInit {
  form: FormGroup;
  vehicules: VehiculeDTO[] = [];
  enCours = false;
  erreur: string | null = null;

  readonly types = ['DIAGNOSTIC', 'REVISION', 'REPARATION', 'CONTROLE', 'PNEUMATIQUES', 'AUTRE'];
  readonly priorites = ['BASSE', 'NORMALE', 'HAUTE', 'URGENTE'];

  constructor(
    private fb: FormBuilder,
    private interventionService: InterventionService,
    private vehiculeService: VehiculeService,
    private router: Router,
  ) {
    this.form = this.fb.group({
      vehiculeId: ['', Validators.required],
      type: ['DIAGNOSTIC', Validators.required],
      descriptionClient: ['', Validators.required],
      priorite: ['NORMALE', Validators.required],
    });
  }

  ngOnInit(): void {
    this.vehiculeService.listerTous().subscribe({
      next: (data) => (this.vehicules = data),
    });
  }

  get f() {
    return this.form.controls;
  }

  onSubmit(): void {
    if (this.form.invalid) {
      this.form.markAllAsTouched();
      return;
    }

    this.enCours = true;
    this.erreur = null;

    const payload = {
      ...this.form.value,
      vehiculeId: Number(this.form.value.vehiculeId),
    };

    this.interventionService.creer(payload).subscribe({
      next: (res) => this.router.navigate(['/interventions', res.id]),
      error: () => {
        this.erreur = "Erreur lors de la création de l'intervention.";
        this.enCours = false;
      },
    });
  }
}
