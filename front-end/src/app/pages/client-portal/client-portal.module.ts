import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ClientPortalRoutingModule } from './client-portal-routing.module';
import { BiddingComponent } from './bidding/bidding.component';


@NgModule({
  declarations: [
  ],
  imports: [
    CommonModule,
    ClientPortalRoutingModule
  ]
})
export class ClientPortalModule { }
