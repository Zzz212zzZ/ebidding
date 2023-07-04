import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BwicOverviewComponent } from './pages/client-portal/bwic-overview/bwic-overview.component';
import { LoginComponent } from './pages/login/login.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { AuthGuardService } from 'src/shared/auth-guard-service.service';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { 
    path: 'dashboard', 
    component: DashboardComponent,
    canActivate: [AuthGuardService], // 添加这一行来应用守卫
    canActivateChild: [AuthGuardService], // 添加这一行来应用守卫
    children: [
      { path: 'welcome', loadChildren: () => import('./pages/welcome/welcome.module').then(m => m.WelcomeModule) },
      { path: 'bwic-overview', component: BwicOverviewComponent },
      // Other routes...
    ]
  },
  { path: '', redirectTo: '/login', pathMatch: 'full' }, // 默认重定向到 login 路由
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
