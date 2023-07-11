import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BwicService } from 'src/app/core/services/bwic.service';
import { NzTabsModule } from 'ng-zorro-antd/tabs';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { OngoingTableComponent } from './ongoing-table/ongoing-table.component';
import { UpcomingTableComponent } from './upcoming-table/upcoming-table.component';
import { EndedTableComponent } from './ended-table/ended-table.component';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatInputModule } from '@angular/material/input';
import { HttpClient } from '@angular/common/http';
import { GptService } from 'src/app/core/services/gpt.service';
import {ProgressBarMode} from '@angular/material/progress-bar';





@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [
    CommonModule,
    NzTabsModule,
    NzIconModule,
    OngoingTableComponent,
    UpcomingTableComponent,
    EndedTableComponent,
    MatCardModule,
    MatDividerModule,
    MatIconModule,
    MatProgressBarModule,
    MatInputModule,

  ],
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.less']
})
export class AdminComponent implements OnInit{

  selectedIndex = 0;

  ongoingData: any[] = [];
  upcomingData: any[] = [];
  endedData: any[] = [];
  gptResponse: string = '';
  message: string = '';
  hover: boolean = false;

  defaultIconType: string = 'sentiment_very_satisfied';  // default icon
  queryIconType: string = 'auto_mode';  // query icon
  defaultProgressBarMode: ProgressBarMode = 'determinate';
  queryProgressBarMode: ProgressBarMode = 'query';

  // Set initial iconType and progressBarMode to default
  submitIconType: string = this.defaultIconType;
  progressBarMode: ProgressBarMode = this.defaultProgressBarMode;


  constructor(private bwicService: BwicService, private http: HttpClient, private gptService: GptService) { }
  
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



  sendMessage(message: string): void {
    // Switch to query icon and progress bar mode when sending a message
    this.submitIconType = this.queryIconType;
    this.progressBarMode = this.queryProgressBarMode;

    this.gptService.traderChatWithGpt(message).subscribe(
      response => {
        // Update the response
        this.gptResponse = response;
        
        // Switch back to default icon and progress bar mode when response is received
        this.submitIconType = this.defaultIconType;
        this.progressBarMode = this.defaultProgressBarMode;
      },
      err => {
        // Handle error
        this.gptResponse = 'An error occurred. Please try again.';

        // Switch back to default icon and progress bar mode when an error occurs
        this.submitIconType = this.defaultIconType;
        this.progressBarMode = this.defaultProgressBarMode;
      }
    );
  }

  ngOnInit(): void {
      // this.bwicService.getBwics();
      



  }

}
