import { Routes } from '@angular/router';
import { LoginComponent } from './login/login';
import { LayoutComponent } from './layout/layout';
import { DashboardComponent } from './dashboard/dashboard';
import { VehiculeListComponent } from './vehicules/vehicule-list/vehicule-list';
import { VehiculeCreateComponent } from './vehicules/vehicule-create/vehicule-create';
import { VehiculeDetailComponent } from './vehicules/vehicule-detail/vehicule-detail';
import { InterventionListComponent } from './interventions/intervention-list/intervention-list';
import { InterventionCreateComponent } from './interventions/intervention-create/intervention-create';
import { InterventionDetailComponent } from './interventions/intervention-detail/intervention-detail';
import { MecanicienListComponent } from './mecaniciens/mecanicien-list/mecanicien-list';
import { MecanicienCreateComponent } from './mecaniciens/mecanicien-create/mecanicien-create';
import { authGuard } from './guards/auth-guard';
import { managerGuard } from './guards/manager-guard';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  {
    path: '',
    component: LayoutComponent,
    canActivate: [authGuard],
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      { path: 'dashboard', component: DashboardComponent },
      { path: 'vehicules', component: VehiculeListComponent },
      { path: 'vehicules/nouveau', component: VehiculeCreateComponent },
      { path: 'vehicules/:id', component: VehiculeDetailComponent },
      { path: 'interventions', component: InterventionListComponent },
      { path: 'interventions/nouvelle', component: InterventionCreateComponent },
      { path: 'interventions/:id', component: InterventionDetailComponent },
      { path: 'mecaniciens', component: MecanicienListComponent },
      { path: 'mecaniciens/nouveau', component: MecanicienCreateComponent, canActivate: [managerGuard] },
    ],
  },
];
