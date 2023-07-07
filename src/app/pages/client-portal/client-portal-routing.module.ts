import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { BiddingComponent } from './bidding/bidding.component';
import { HistoryComponent } from './history/history.component';

import { HomepageComponent } from './homepage/homepage.component';

const routes: Routes = [
  { path: '', redirectTo: 'homepage', pathMatch: 'full',data: { expectedRole: 'client' } }, // redirect to `homepage`
  { path: 'homepage', component: HomepageComponent,data: { expectedRole: 'client' }},
  { path: 'bidding', component: BiddingComponent ,data: { expectedRole: 'client' }},
  { path: 'history', component: HistoryComponent ,data: { expectedRole: 'client' }}
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ClientPortalRoutingModule { }
