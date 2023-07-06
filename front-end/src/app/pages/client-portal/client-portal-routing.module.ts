import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BwicOverviewComponent } from './bwic-overview/bwic-overview.component';
import { BiddingComponent } from './bidding/bidding.component';
import { HistoryComponent } from './history/history.component';

const routes: Routes = [
  {path:'bidding',component:BiddingComponent},
  {path:'history',component:HistoryComponent}
  
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ClientPortalRoutingModule { }
