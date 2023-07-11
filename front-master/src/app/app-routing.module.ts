import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LayoutComponent } from './pages/layout/layout.component';
import { AuthGuardService } from 'src/app/core/guard/auth-guard-service.service';

const routes: Routes = [
  { path: 'login', loadComponent: () => import('./pages/login/login.component').then(m => m.LoginComponent) },
  {
    path: 'layout',
    component: LayoutComponent,
    canActivate: [AuthGuardService], // 添加这一行来应用守卫
    canActivateChild: [AuthGuardService], // 添加这一行来应用守卫
    children: [
      {
        path: 'client',
        loadChildren: () => import('./pages/client-portal/client-portal.module').then(m => m.ClientPortalModule),
        data: { expectedRole: 'client' }
      },
      {
        path: 'trader',
        loadChildren: () => import('./pages/trader-portal/trader-portal.module').then(m => m.TraderPortalModule),
        data: { expectedRole: 'trader' }
      },
    ]
  },
  { path: '', redirectTo: '/login', pathMatch: 'full' }, // 默认重定向到 login 路由
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
