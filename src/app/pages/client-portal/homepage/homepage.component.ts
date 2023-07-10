import { CommonModule } from '@angular/common';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzCardModule } from 'ng-zorro-antd/card';
import { NzTableModule } from 'ng-zorro-antd/table';
import { NzDividerModule } from 'ng-zorro-antd/divider';
import { AfterViewInit, Component, ElementRef, Input, OnDestroy, OnInit, ViewChild } from '@angular/core';
import { EChartsOption} from 'echarts';
import * as echarts from 'echarts';
import { NgxEchartsModule } from 'ngx-echarts';
import { MatCardModule } from '@angular/material/card';
import { NzCarouselModule } from 'ng-zorro-antd/carousel';



@Component({
  selector: 'app-homepage',
  standalone: true,
  imports: [CommonModule,NzInputModule, 
    NzIconModule,NzButtonModule,
    NzFormModule, NzCardModule,
    NzTableModule,NzDividerModule,
    NgxEchartsModule, MatCardModule,NzCarouselModule],
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.less']
})


export class HomepageComponent implements OnInit {
 
  constructor() {
    console.log(echarts)
  }
 
  ngOnInit() {
    this.initCharts();
  }
 
  initCharts(){
    const ec = echarts as any;
    let lineChart = ec.init(document.getElementById('lineChart'));
    let lineChart2 = ec.init(document.getElementById('lineChart2'));
    let lineChart3 = ec.init(document.getElementById('lineChart3'));

    let lineChartOption ={
            tooltip : {
                trigger: 'axis'
            },
            toolbox: {
                show : false,
            },
            legend:{
                padding:0
            },
            xAxis : [
                {
                    type : 'category',
                    boundaryGap : false,
                    data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : [
                {
                    name:'合同额',
                    type:'line',
                    smooth:true,
                    itemStyle : {
                        normal : {
                            lineStyle:{
                                color:'#c8c8c8'
                            }
                        }
                    },
                    data:[10, 2, 5, 4, 6, 3, 7,2,2,3,6,7],
 
                },
                {
                    name:'营业收入',
                    type:'line',
                    smooth:true,
                    itemStyle: {
                        normal : {
                            lineStyle:{
                                color:'#1ab394'
                            }
                        }
                    },
                    data:[3, 2, 4, 7, 0, 3, 1,3,4,1,2,3]
                },
                {
                    name:'公司净利润',
                    type:'line',
                    smooth:true,
                    itemStyle: {
                        normal : {
                            lineStyle:{
                                color:'#ff713a'
                            }
                        }
                    },
                    data:[10, 2, 6, 3, 2, 9, 10,3,4,8,4,3]
                }
            ]
        };
        lineChart.setOption(lineChartOption);
        lineChart2.setOption(lineChartOption);
        lineChart3.setOption(lineChartOption);

    }
}