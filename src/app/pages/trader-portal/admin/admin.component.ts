import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BwicService } from 'src/app/core/services/bwic.service';
import { NzTabsModule } from 'ng-zorro-antd/tabs';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { OngoingTableComponent } from './ongoing-table/ongoing-table.component';
import { UpcomingTableComponent } from './upcoming-table/upcoming-table.component';
import { EndedTableComponent } from './ended-table/ended-table.component';





@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [
    CommonModule,
    NzTabsModule,
    NzIconModule,
    OngoingTableComponent,
    UpcomingTableComponent,
    EndedTableComponent
  ],
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.less']
})
export class AdminComponent implements OnInit{

  ongoingData: any[] = [];
  upcomingData: any[] = [];
  endedData: any[] = [];
  
  constructor(private bwicService: BwicService){}
  selectedTab = 'Ongoing';
  tabs = [
    {
      name: 'Ongoing',
      iconType: 'hourglass',
      iconSpin: true,
      iconTheme: 'twotone',
    },
    {
      name: 'Upcoming',
      iconType: 'notification',
      iconSpin: false,
      iconTheme: 'twotone',

    },
    {
      name: 'Ended',
      iconType: 'trophy',
      iconSpin: false,
      iconTheme: 'twotone',
    }
  ];
  
  tabChange(index: number): void {
    this.selectedTab = this.tabs[index].name;
  }


  ngOnInit(): void {
      // this.bwicService.getBwics();
      



  }

}
