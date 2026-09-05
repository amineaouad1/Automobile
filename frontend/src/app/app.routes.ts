import { Routes } from '@angular/router';
import { VehiculeListComponent } from './vehicules/vehicule-list/vehicule-list';
import { VehiculeCreateComponent } from './vehicules/vehicule-create/vehicule-create';

export const routes: Routes = [
  { path: '', redirectTo: 'vehicules', pathMatch: 'full' },
  { path: 'vehicules', component: VehiculeListComponent },
  { path: 'vehicules/nouveau', component: VehiculeCreateComponent },
];
