import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RouterModule } from '@angular/router';

import { NzLayoutModule } from 'ng-zorro-antd/layout';
import { NzMenuModule } from 'ng-zorro-antd/menu';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { SiderComponent } from './sider/sider.component';


@Component({
  selector: 'app-layout',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule, // 如果你的组件中使用了路由，那么也需要导入RouterModule
    NzLayoutModule,
    NzMenuModule,
    NzIconModule,
    SiderComponent
  ],

  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.less']
})
export class LayoutComponent {
  isCollapsed = false;


  logout(): void{
    localStorage.clear();//清除localstorage
    window.location.href = `http://localhost:${window.location.port}/login`;
  }
}
