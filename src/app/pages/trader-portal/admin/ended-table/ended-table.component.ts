import { Component, Input, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-ended-table',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './ended-table.component.html',
  styleUrls: ['./ended-table.component.less']
})
export class EndedTableComponent implements OnInit{
  @Input() data: any[] = [];

  ngOnInit(): void {
    console.log('EndedTableComponent ngOnInit');
    console.log(this.data);
  }

}
