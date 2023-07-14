import { Component,ViewChild, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RouterModule } from '@angular/router';

import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSidenavModule, MatSidenav } from '@angular/material/sidenav';  
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { MatDividerModule } from '@angular/material/divider';
import { MatDrawerMode } from '@angular/material/sidenav';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import { FormsModule } from '@angular/forms';
import { ClientMenu,TraderMenu,avatarUrl } from './roleProperties';



export interface MenuItem {
  path: string,
  desc: string,
  icon: string
}

@Component({
  selector: 'app-trader-layout',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule, // 如果你的组件中使用了路由，那么也需要导入RouterModule
    MatToolbarModule,
    MatSidenavModule,
    MatButtonModule,
    MatIconModule,
    MatDividerModule,
    MatSlideToggleModule,
    FormsModule,
    

  ],

  templateUrl: './trader-layout.component.html',
  styleUrls: ['./trader-layout.component.less']
})
export class TraderLayoutComponent {
  sidenavMode: MatDrawerMode = 'over';
  isChecked = false;
  @Input() isCollapsed = false;
  menus: MenuItem[]=[];  // 根据身份动态加载
  role: string = ''; // 用来存储用户角色
  avatar: string = ''; // 用来存储用户头像的 URL
  username: string = ''; // 用来存储用户名
  @ViewChild('sidenav') sidenav!: MatSidenav;


  ngOnInit() {
    this.role = localStorage.getItem('role') || '';
    this.avatar = avatarUrl[this.role as keyof typeof avatarUrl] || '';
    this.username = localStorage.getItem('name') || '';


    if (this.role === 'client') {
      this.menus = ClientMenu;
      console.log(this.menus);
    } else if (this.role === 'trader') {
      this.menus = TraderMenu;
    }
  }


  toggleSidenavMode(event: any) {
    this.sidenavMode = event.checked ? 'side' : 'over';
    if (this.sidenavMode === 'over' && this.sidenav.opened) {
      this.sidenav.toggle();
    }
  }


  logout(): void{
    localStorage.clear();//清除localstorage
    window.location.href = `http://47.108.72.83:${window.location.port}/login`;
    // window.location.href = `http://localhost:${window.location.port}/login`;
  }
}
