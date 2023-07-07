import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-upcoming-table',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './upcoming-table.component.html',
  styleUrls: ['./upcoming-table.component.less']
})
export class UpcomingTableComponent implements OnInit{

  @Input() data: any[] = [];

  ngOnInit(): void {
    console.log('UpcomingTableComponent ngOnInit');
    console.log(this.data);
  }

}
