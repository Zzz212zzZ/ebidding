import { Component, OnInit, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NzTableModule } from 'ng-zorro-antd/table';
import { NzDrawerModule } from 'ng-zorro-antd/drawer';
import { NzDrawerService } from 'ng-zorro-antd/drawer';
import { BwicService,BwicUpcomingFullRecord } from 'src/app/core/services/bwic.service';
import { FormsModule } from '@angular/forms';
import { NzPopconfirmModule } from 'ng-zorro-antd/popconfirm';

export interface BwicUpcomingRecord {
  bwicId: string;
  bondId: string;
  cusip: string;
  issuer: string;
  size: string;
  startPrice: string;
  startTime: string;
  dueTime: string;
}

@Component({
  selector: 'app-upcoming-table',
  standalone: true,
  imports: [
    CommonModule,
    NzTableModule,
    NzDrawerModule,
    FormsModule,
    NzPopconfirmModule
  ],
  templateUrl: './upcoming-table.component.html',
  styleUrls: ['./upcoming-table.component.less']
})
export class UpcomingTableComponent implements OnInit {
  @Input() data: BwicUpcomingRecord[] = [];
  editCache: { [key: string]: { edit: boolean; data: BwicUpcomingRecord } } = {};

  constructor(private drawerService: NzDrawerService, private bwicService: BwicService) { }

  ngOnInit(): void {
    console.log("upcoming-table.component.ts: ngOnInit(): void");
    this.getBwics();
    
  }

  getBwics(): void {
    this.bwicService.getUpcomingBwics().subscribe(data => {
      this.data = data;
      this.updateEditCache();
    });
  }

  startEdit(id: string): void {
    this.editCache[id].edit = true;
  }

  cancelEdit(id: string): void {
    const index = this.data.findIndex(item => item.bwicId === id);
    this.editCache[id] = {
      data: { ...this.data[index] },
      edit: false
    };
  }

  saveEdit(id: string): void {
    const index = this.data.findIndex(item => item.bwicId === id);
    const updatedData: BwicUpcomingRecord = this.editCache[id].data;
    
    const convertedData: BwicUpcomingFullRecord = {
      bwicId: updatedData.bwicId,
      bondId: updatedData.bondId,
      size: Number(updatedData.size),
      startPrice: Number(updatedData.startPrice),
      startTime: new Date(updatedData.startTime),
      dueTime: new Date(updatedData.dueTime)
    };
    
    Object.assign(this.data[index], convertedData);
    this.editCache[id].edit = false;
    
    this.bwicService.updateBwicUpcomingFullRecord(id, convertedData).subscribe(
      () => console.log('Update successful'),
      err => console.error('Update failed', err)
    );
  }
  
  

  updateEditCache(): void {
    this.data.forEach(item => {
      this.editCache[item.bwicId] = {
        edit: false,
        data: { ...item }
      };
    });
  }
}
