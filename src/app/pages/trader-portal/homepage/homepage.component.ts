import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import * as echarts from 'echarts';
import { BwicService } from 'src/app/core/services/bwic.service';

export interface Bwics {
  bwicId: number,
  bondId: string,
  size: number,
  startPrice: number,
  presentPrice: number,
  startTime: string,
  dueTime: string,
  lastBidTime: string,
  bidCounts: number,
}


@Component({
  selector: 'app-homepage',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.less']
})
export class HomepageComponent implements OnInit {

  AllBwics: Bwics[] = [];
  IdData1: number[] = [];
  IdData2: number[] = [];
  CountsData: number[] = [];
  StartPriceData: number[] = [];
  PresentPriceData: number[] = [];
  constructor(private bwicService: BwicService) {
    // console.log(echarts)
  }

  ngOnInit() {
    this.bwicService.getAllBwics().subscribe((data: Bwics[]) => {
      this.AllBwics = data;
      let sortedBwics = [...this.AllBwics]; // 创建副本用于排序
      sortedBwics.sort((a, b) => b.bidCounts - a.bidCounts);
      //第一个表
      for (let i = 5; i > 0; i--) {
        this.CountsData.push(sortedBwics[i].bidCounts);
        this.IdData1.push(sortedBwics[i].bwicId);
      }
      //第二个表
      for (let i = 0; i < this.AllBwics.length; i++) {
        this.IdData2.push(this.AllBwics[i].bwicId);
        this.StartPriceData.push(this.AllBwics[i].startPrice);
        this.PresentPriceData.push(this.AllBwics[i].presentPrice);
      }
      //必须在后端获取数据后调用下列方法来生成图表。
      this.Bar();
      this.initCharts();
    });

  }
  Bar() {
    const ec = echarts as any;
    let bar = ec.init(document.getElementById('bar'));
    let barOption = {
      title: {
        text: 'Popular BWIC'
      },
      tooltip: {
        trigger: 'axis',
        axisPointer: {
          type: 'shadow'
        }
      },
      legend: {},
      grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
      },
      xAxis: {
        type: 'value',
        boundaryGap: [0, 0.01]
      },
      yAxis: {
        type: 'category',
        data: this.IdData1,
        name: 'BwicId'
      },
      series: [
        {
          name: 'TotalCounts',
          type: 'bar',
          data: this.CountsData
        },
      ]
    };
    bar.setOption(barOption);
  }
  initCharts() {
    const ec = echarts as any;
    let lineChart = ec.init(document.getElementById('lineChart'));
    let lineChartOption = {
      title: {
        text: 'Price Difference'
      },
      tooltip: {
        trigger: 'axis'
      },
      toolbox: {
        show: false,
      },
      legend: {
        padding: 0
      },
      xAxis: [
        {
          type: 'category',
          boundaryGap: false,
          data: this.IdData2,
          name: 'BwicId'
        }
      ],
      yAxis: [
        {
          type: 'value'
        }
      ],
      series: [
        {
          name: 'StartPrice',
          type: 'line',
          smooth: true,
          lineStyle: {
            color: '#ff713a'
          },
          data: this.StartPriceData
        },
        {
          name: 'PresentPrice',
          type: 'line',
          smooth: true,
          lineStyle: {
            color: '#1ab394'
          },
          data: this.PresentPriceData
        }
      ]
    };
    lineChart.setOption(lineChartOption);
  }
}

