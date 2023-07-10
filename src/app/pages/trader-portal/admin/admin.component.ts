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

  selectedIndex = 0;

  ongoingData: any[] = [];
  upcomingData: any[] = [];
  endedData: any[] = [];
  
  constructor(private bwicService: BwicService){}
  active_color="#ebaa96";
  inactive_color="#1890ff";

  selectedTab = 'Ongoing';
  tabs = [
    {
      name: 'Ongoing',
      iconType: 'hourglass',
      iconSpin: true, // initially false
      iconTheme: 'twotone',
      color: this.active_color, 
    },
    {
      name: 'Upcoming',
      iconType: 'notification',
      iconSpin: false, // initially false
      iconTheme: 'twotone',
      color: this.inactive_color, // default color
    },
    {
      name: 'Ended',
      iconType: 'trophy',
      iconSpin: false, // initially false
      iconTheme: 'twotone',
      color: this.inactive_color, // default color
    }
  ];
  
  tabChange(index: number): void {
    this.tabs.forEach((tab, i) => {
      if(i === index){
        tab.iconSpin = true;
        tab.color = this.active_color; // active color
      }
      else {
        tab.iconSpin = false;
        tab.color = this.inactive_color; // default color
      }
    });
    this.selectedTab = this.tabs[index].name;
  }


  ngOnInit(): void {
      // this.bwicService.getBwics();
      



  }

}
