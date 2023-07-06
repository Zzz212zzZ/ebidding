import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './admin/admin.component';
import { PopularComponent } from './popular/popular.component';

const routes: Routes = [
  {path:'admin', component: AdminComponent},
  {path:'popular', component: PopularComponent},
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class TraderPortalRoutingModule { }
