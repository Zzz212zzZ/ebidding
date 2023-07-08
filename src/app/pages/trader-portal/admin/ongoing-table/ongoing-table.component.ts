import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NzTableModule } from 'ng-zorro-antd/table';
import { NzDrawerModule } from 'ng-zorro-antd/drawer';
import { NzDrawerService } from 'ng-zorro-antd/drawer';
import { DrawerContentComponent } from './drawer-content/drawer-content.component';
import { BwicService } from 'src/app/core/services/bwic.service';



export interface BidRankItemData {
  bid_id: number;
  account_id: number;
  account_name: string;
  price: number;
  ranking: number;
  time: string;
}



export interface ParentItemData {
  bwic_id: number;
  bond_id: string;
  cusip: string;
  issuer: string;
  size: number;
  start_time: string;
  due_time: string;
  last_bid_time: string;
  bid_counts: number;
  expand: boolean;
  children: BidRankItemData[];
}





@Component({
  selector: 'app-ongoing-table',
  standalone: true,
  imports: [
    CommonModule,
    NzTableModule,
    NzDrawerModule
  ],
  templateUrl: './ongoing-table.component.html',
  styleUrls: ['./ongoing-table.component.less']
})


export class OngoingTableComponent {


constructor(private drawerService: NzDrawerService, private bwicService: BwicService) {}

  selectedRow: ParentItemData | null = null;

  @Input() data: any[] = [];
  listOfParentData: ParentItemData[] = [];
  listOfBidRankData: BidRankItemData[] = [];

  ngOnInit(): void {
    console.log("ongoing-table.component.ts: ngOnInit(): void");
    this.getBwics();
  }

  getBwics(): void {
    this.bwicService.getOngoingBwics().subscribe(data => this.listOfParentData = data);
  }



  onRowClick(data: ParentItemData): void {
    this.selectedRow = data;
    console.log('Row clicked: ', data);

    const drawerRef = this.drawerService.create({
      nzTitle: '详细信息',
      nzPlacement: 'right',
      nzWidth: '80%',  // 宽度设为屏幕的 80%
      nzBodyStyle: {   // 自定义抽屉的样式
        padding: '20px',
        backgroundColor: '#f0f2f5', // 设置背景颜色为淡灰色
      },
      nzContent: DrawerContentComponent,
      nzContentParams: {
        data: [
          {
            rank: 1,
            price: 10,
            account_id: 'A001',
            account_name: 'John Doe',
            transaction_time: '2023-07-08 10:00:00'
          },
          {
            rank: 2,
            price: 20,
            account_id: 'A002',
            account_name: 'Jane Smith',
            transaction_time: '2023-07-08 11:00:00'
          },
          {
            rank: 3,
            price: 15,
            account_id: 'A003',
            account_name: 'Bob Johnson',
            transaction_time: '2023-07-08 09:00:00'
          },
          {
            rank: 4,
            price: 25,
            account_id: 'A004',
            account_name: 'Alice Williams',
            transaction_time: '2023-07-08 12:00:00'
          }
        ]
      }
    });
  }
  


  


}

