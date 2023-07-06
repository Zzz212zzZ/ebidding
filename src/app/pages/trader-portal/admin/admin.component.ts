import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BwicService } from 'src/app/core/services/bwic.service';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.less']
})
export class AdminComponent implements OnInit{
  constructor(private bwicService: BwicService){}
 
  ngOnInit(): void {
      this.bwicService.getBwics();
  }

}
