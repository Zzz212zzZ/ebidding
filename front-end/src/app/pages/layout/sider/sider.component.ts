import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';

import { NzLayoutModule } from 'ng-zorro-antd/layout';
import { NzMenuModule } from 'ng-zorro-antd/menu';
import { NzIconModule } from 'ng-zorro-antd/icon';


export interface SubMenu{
  path: string,
  desc: string,
}
export interface Menu{
  title: string,
  icon: string,
  children: SubMenu[]
}


@Component({
  selector: 'app-sider',
  standalone: true,
  imports: [
    CommonModule,
    RouterModule, // 如果你的组件中使用了路由，那么也需要导入RouterModule
    NzLayoutModule,
    NzMenuModule,
    NzIconModule,
  ],
  templateUrl: './sider.component.html',
  styleUrls: ['./sider.component.less']

})
export class SiderComponent {
  @Input() isCollapsed = false;

  menus: Menu[] = [
    {
      title: 'Dashboard',
      icon: 'dashboard',
      children: [
        {
          path: '/layout/welcome',
          desc: 'Welcome'
        }
      ]
    },
    {
      title: 'Client',
      icon: 'pie-chart',
      children: [
        {
          path: '/layout/client/bidding',
          desc: 'Bidding'
        },
        {
          path: '/layout/client/history',
          desc: 'History'
        },
      ]
    },
    {
      title: 'Sales',
      icon: 'pie-chart',
      children: [
        {
          path: '/layout/sales/admin',
          desc: 'Admin'
        },
        {
          path: '/layout/sales/popular',
          desc: 'Popular'
        },
      ]
    },
  ];
}
