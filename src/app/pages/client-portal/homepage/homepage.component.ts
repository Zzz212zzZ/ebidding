import { CommonModule } from '@angular/common';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzCardModule } from 'ng-zorro-antd/card';
import { NzTableModule } from 'ng-zorro-antd/table';
import { NzDividerModule } from 'ng-zorro-antd/divider';
import { Component, OnInit } from '@angular/core';
import * as echarts from 'echarts';


//interface定义一种类型
interface Person {
  key: string;
  name: string;
  age: number;
  address: string;
}

@Component({
  selector: 'app-homepage',
  standalone: true,
  imports: [CommonModule,NzInputModule, 
    NzIconModule,NzButtonModule,
    NzFormModule, NzCardModule,
    NzTableModule,NzDividerModule],
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.less']
})


export class HomepageComponent implements OnInit{
  title = 'myapp';
 
ngOnInit() {
this.initChart()

}
initChart() {
  var chartDom = document.getElementById('testchart');
 // var myChart = echarts.init(chartDom);
  var option;
   
  option = {
  xAxis: {
  type: 'category',
  data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
  },
  yAxis: {
  type: 'value'
  },
  series: [{
  data: [120, 200, 150, 80, 70, 110, 130],
  type: 'bar',
  showBackground: true,
  backgroundStyle: {
  color: 'rgba(180, 180, 180, 0.2)'
  }
  }]
  };
  //option && myChart.setOption(option);
  }
  }
