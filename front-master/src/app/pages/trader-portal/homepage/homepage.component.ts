import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import * as echarts from 'echarts';
import { BwicService } from 'src/app/core/services/bwic.service';
import { NzCarouselModule } from 'ng-zorro-antd/carousel';

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
  imports: [CommonModule, NzCarouselModule],
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
      // 创建副本用于排序
      const sortedBwics = [...this.AllBwics].sort((a, b) => b.bidCounts - a.bidCounts);

      // 第一张表
      this.CountsData = sortedBwics.slice(0, 5).map(bwic => bwic.bidCounts).reverse();
      this.IdData1 = sortedBwics.slice(0, 5).map(bwic => bwic.bwicId).reverse();

      // 第二张表
      this.IdData2 = this.AllBwics.map(bwic => bwic.bwicId);
      this.StartPriceData = this.AllBwics.map(bwic => bwic.startPrice);
      this.PresentPriceData = this.AllBwics.map(bwic => bwic.presentPrice);
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
      toolbox: {
        feature: {
          saveAsImage: {}
        }
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
          data: this.CountsData,
          itemStyle: {
            color: '#2b473e'
          }
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
        feature: {
          saveAsImage: {}
        }
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

