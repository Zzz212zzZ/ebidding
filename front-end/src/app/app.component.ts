import { Component, OnInit } from '@angular/core';
import { UserService } from './core/services/user.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.less']
})

export class AppComponent implements OnInit{
  constructor(private userService: UserService){
  }

  ngOnInit(): void {
    const user = this.userService.getUser();
  }
}