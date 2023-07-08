import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NzTableModule } from 'ng-zorro-antd/table';




interface ParentItemData {
  bwic_id: number;
  issuer: string;
  size: number;
  start_time: string;
  due_time: string;
  last_bid_time: string;
  bid_counts: number;
  expand: boolean;
}


interface ChildrenItemData {
  bid_id: number;
  account_id: number;
  account_name: string;
  price: number;
  ranking: number;
  time: string;
}


@Component({
  selector: 'app-ongoing-table',
  standalone: true,
  imports: [
    CommonModule,
    NzTableModule
  ],
  templateUrl: './ongoing-table.component.html',
  styleUrls: ['./ongoing-table.component.less']
})


export class OngoingTableComponent implements OnInit {

  @Input() data: any[] = [];
  listOfParentData: ParentItemData[] = [];
  listOfChildrenData: ChildrenItemData[] = [];

  

  ngOnInit(): void {
    for (let i = 0; i < 3; ++i) {
      this.listOfParentData.push({
        bwic_id: i,
        issuer: 'Issuer ' + i,
        size: 500,
        start_time: '2023-07-07 23:12:00',
        due_time: '2023-07-08 23:12:00',
        last_bid_time: '2023-07-09 23:12:00',
        bid_counts: 20,
        expand: false
      });
    }
    for (let i = 0; i < 3; ++i) {
      this.listOfChildrenData.push({
        bid_id: i,
        account_id: i,
        account_name: 'Account ' + i,
        price: 100,
        ranking: i + 1,
        time: '2023-07-07 23:12:00'
      });
    }
  }
  

}
