import { Component, OnInit } from '@angular/core';
import { DataItem } from './data-item.model';

@Component({
  selector: 'app-bwic-overview',
  templateUrl: './bwic-overview.component.html',
  styleUrls: ['./bwic-overview.component.less']
})
export class BwicOverviewComponent implements OnInit {
  dataItems: DataItem[] = [
    {bwic_id: BigInt(1), bond_id: '0', size: 1000, start_price: 98.5, present_price: 103, start_time: new Date('2022-01-01'), due_time: new Date('2024-12-31'), last_bid_time: new Date('2023-07-03'), bid_counts: BigInt(9)},
    {bwic_id: BigInt(2), bond_id: '1', size: 500, start_price: 102.3, present_price: 101.8, start_time: new Date('2022-03-15'), due_time: new Date('2024-11-30'), last_bid_time: new Date('2023-06-30'), bid_counts: BigInt(3)},
  ];

  ngOnInit() {
    this.printDataItems();
  }

  printDataItems() {
    for (let item of this.dataItems) {
      console.log(item);
    }
  }
}
