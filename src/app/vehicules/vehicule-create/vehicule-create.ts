import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { VehiculeService } from '../../services/vehicule';
import { CreerVehiculeRequest } from '../../models/vehicule.model';

@Component({
  selector: 'app-vehicule-create',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './vehicule-create.html',
})
export class VehiculeCreateComponent {
  vehiculeForm: FormGroup;
  enCours = false;
  erreur: string | null = null;

  anneeMax = new Date().getFullYear() + 1;

  constructor(
    private fb: FormBuilder,
    private vehiculeService: VehiculeService,
    private router: Router,
  ) {
    this.vehiculeForm = this.fb.group({
      immatriculation: [
        '',
        [Validators.required, Validators.minLength(4), Validators.maxLength(20)],
      ],
      marque: ['', [Validators.required, Validators.maxLength(50)]],
      modele: ['', [Validators.required, Validators.maxLength(50)]],
      annee: [null, [Validators.required, Validators.min(1950), Validators.max(this.anneeMax)]],
      kilometrage: [null, [Validators.required, Validators.min(0)]],
      clientId: [null, [Validators.required, Validators.min(1)]],
    });
  }

  get f() {
    return this.vehiculeForm.controls;
  }

  onSubmit(): void {
    if (this.vehiculeForm.invalid) {
      this.vehiculeForm.markAllAsTouched();
      return;
    }

    this.enCours = true;
    this.erreur = null;

    const request: CreerVehiculeRequest = this.vehiculeForm.value;

    this.vehiculeService.creer(request).subscribe({
      next: () => {
        this.router.navigate(['/vehicules']);
      },
      error: (err) => {
        this.erreur = 'Erreur lors de la création du véhicule. Vérifiez les données saisies.';
        this.enCours = false;
        console.error(err);
      },
    });
  }

  onAnnuler(): void {
    this.router.navigate(['/vehicules']);
  }
}
