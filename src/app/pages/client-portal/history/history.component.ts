import { Component,OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzTableModule } from 'ng-zorro-antd/table';
import { NzBadgeModule } from 'ng-zorro-antd/badge';
import { NzDropDownModule } from 'ng-zorro-antd/dropdown';
import { NzDividerModule } from 'ng-zorro-antd/divider';
import { NzGridModule } from 'ng-zorro-antd/grid';

interface BwicItemData {
  key:number;
  bwicId: number;
  issuer: string;
  cussip: string;
  startTime: string;
  dueTime: string;
  startPrice: number;
  size:number;
  expand: boolean;
}

interface BidHistoryItemData {
  key: number;
  time: string;
  price: string;
  upgradeRank: number;
}

@Component({
  selector: 'app-history',
  standalone: true,
  imports: [CommonModule,
    NzInputModule,
    NzIconModule,
    NzButtonModule,
    NzTableModule,
    NzBadgeModule,
    NzDropDownModule,
    NzDividerModule,
    NzGridModule],
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.less']
})

export class HistoryComponent implements OnInit {
  listOfBwicData: BwicItemData[] = [];
  listOfBidHistoryData: BidHistoryItemData[] = [];

  ngOnInit(): void {
    for (let i = 0; i < 3; ++i) {
      this.listOfBwicData.push({
        key:i,
        bwicId: i+1,
        issuer: 'LIBERTY MUTUAL INSURANCE GROUP',
        cussip: '310867UR2',
        startTime: '2023-07-07 16:23:00',
        dueTime: '2024-12-24 23:12:00',
        startPrice: 100,
        size:100,
        expand: false
      });
    }
    for (let i = 0; i < 3; ++i) {
      this.listOfBidHistoryData.push({
        key: i,
        time: '2014-12-24 23:12:00',
        price: 'This is history ebidding-price',
        upgradeRank: 1
      });
    }
  }
}
