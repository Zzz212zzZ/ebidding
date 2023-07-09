import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NzTableModule, NzTableFilterFn, NzTableFilterList, NzTableSortFn, NzTableSortOrder } from 'ng-zorro-antd/table';
import { BidRankItemData } from '../ongoing-table.component';

interface ColumnItem {
  name: string;
  sortOrder: NzTableSortOrder | null;
  sortFn: NzTableSortFn<BidRankItemData> | null;
  listOfFilter: NzTableFilterList;
  filterFn: NzTableFilterFn<BidRankItemData> | null;
}

@Component({
  selector: 'app-drawer-content',
  standalone: true,
  imports: [CommonModule, NzTableModule],
  templateUrl: './drawer-content.component.html',
  styleUrls: ['./drawer-content.component.less']
})

export class DrawerContentComponent {
  @Input() data: BidRankItemData[] = []; // 需要显示的数据

  public visible = true; // 控制抽屉是否可见

  listOfColumns: ColumnItem[] = [
    {
      name: 'Rank',
      sortOrder: 'ascend',
      sortFn: (a: BidRankItemData, b: BidRankItemData) => a.ranking - b.ranking,
      listOfFilter: [],
      filterFn: null
    },
    {
      name: 'Price',
      sortOrder: null,
      sortFn: (a: BidRankItemData, b: BidRankItemData) => a.price - b.price,
      listOfFilter: [],
      filterFn: null
    },
    {
      name: 'Account ID',
      sortOrder: null,
      sortFn: (a: BidRankItemData, b: BidRankItemData) => String(a.accountId).localeCompare(String(b.accountId)),
      listOfFilter: [],
      filterFn: null
    },
    {
      name: 'Account Name',
      sortOrder: null,
      sortFn: (a: BidRankItemData, b: BidRankItemData) => a.accountName.localeCompare(b.accountName),
      listOfFilter: [],
      filterFn: null
    },
    {
      name: 'Transaction Time',
      sortOrder: null,
      sortFn: (a: BidRankItemData, b: BidRankItemData) => a.time.localeCompare(b.time),
      listOfFilter: [],
      filterFn: null
    }
  ];

  trackByName(_: number, item: ColumnItem): string {
    return item.name;
  }

  sortByRank(): void {
    this.listOfColumns.forEach(item => {
      if (item.name === 'Rank') {
        item.sortOrder = 'ascend';
      } else {
        item.sortOrder = null;
      }
    });
  }

  sortByAccountId(): void {
    this.listOfColumns.forEach(item => {
      if (item.name === 'Account ID') {
        item.sortOrder = 'ascend';
      } else {
        item.sortOrder = null;
      }
    });
  }

  sortByAccountName(): void {
    this.listOfColumns.forEach(item => {
      if (item.name === 'Account Name') {
        item.sortOrder = 'ascend';
      } else {
        item.sortOrder = null;
      }
    });
  }

  sortByTransactionTime(): void {
    this.listOfColumns.forEach(item => {
      if (item.name === 'Transaction Time') {
        item.sortOrder = 'ascend';
      } else {
        item.sortOrder = null;
      }
    });
  }

  resetFilters(): void {
    this.listOfColumns.forEach(item => {
      item.listOfFilter = [];
    });
  }

  resetSortAndFilters(): void {
    this.listOfColumns.forEach(item => {
      item.sortOrder = null;
      item.listOfFilter = [];
    });
  }

  ngOnInit(): void {
    console.log(this.data);
  }
}