import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MecanicienService } from '../../services/mecanicien';
import { CreerMecanicienRequest } from '../../models/mecanicien.model';

@Component({
  selector: 'app-mecanicien-create',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './mecanicien-create.html',
})
export class MecanicienCreateComponent {
  form: FormGroup;
  enCours = false;
  erreur: string | null = null;

  constructor(
    private fb: FormBuilder,
    private mecanicienService: MecanicienService,
    private router: Router,
  ) {
    this.form = this.fb.group({
      nom: ['', [Validators.required, Validators.maxLength(100)]],
      specialite: ['', [Validators.required, Validators.maxLength(100)]],
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

    const request: CreerMecanicienRequest = this.form.value;

    this.mecanicienService.creer(request).subscribe({
      next: () => this.router.navigate(['/mecaniciens']),
      error: () => {
        this.erreur = "Erreur lors de la création du mécanicien.";
        this.enCours = false;
      },
    });
  }

  onAnnuler(): void {
    this.router.navigate(['/mecaniciens']);
  }
}
