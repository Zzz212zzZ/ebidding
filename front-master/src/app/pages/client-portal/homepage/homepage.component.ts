import { CommonModule } from '@angular/common';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzCardModule } from 'ng-zorro-antd/card';
import { NzTableModule } from 'ng-zorro-antd/table';
import { NzDividerModule } from 'ng-zorro-antd/divider';
import { AfterViewInit, Component, ElementRef, Input, OnDestroy, OnInit, ViewChild } from '@angular/core';
import * as echarts from 'echarts';
import { NzCarouselModule } from 'ng-zorro-antd/carousel';
import { BwicService } from 'src/app/core/services/bwic.service';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatIconModule } from '@angular/material/icon';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatInputModule } from '@angular/material/input';

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
    imports: [CommonModule, NzInputModule,
        NzIconModule, NzButtonModule,
        NzFormModule, NzCardModule,
        NzTableModule, NzDividerModule,
        MatCardModule, NzCarouselModule,
        MatDividerModule, MatIconModule,
        MatProgressBarModule, MatInputModule],
    templateUrl: './homepage.component.html',
    styleUrls: ['./homepage.component.less']
})


export class HomepageComponent implements OnInit {
    AllBwics: Bwics[] = [];
    IdData1: number[] = [];
    IdData2: number[] = [];
    CountsData: number[] = [];
    SizeData: number[] = [];
    StartPriceData: number[] = [];
    constructor(private bwicService: BwicService) { }

    ngOnInit() {
        this.bwicService.getAllBwics().subscribe((data: Bwics[]) => {
            this.AllBwics = data;
            //Popular Bwic
            const currentTime = new Date();
            const filteredBwics1 = this.AllBwics.filter(bwic => {
                const startTime = new Date(bwic.startTime);
                const dueTime = new Date(bwic.dueTime);
                return startTime <= currentTime && currentTime <= dueTime;
            });
            const sortedBwics = [...filteredBwics1].sort((a, b) => b.bidCounts - a.bidCounts);
            const topFiveBwics = sortedBwics.slice(0, 5).reverse();
            this.CountsData = topFiveBwics.map(bwic => bwic.bidCounts);
            this.IdData1 = topFiveBwics.map(bwic => bwic.bwicId);

            //Uncoming Bwic
            const filteredBwics2 = this.AllBwics.filter(bwic => {
                const startTime = new Date(bwic.startTime);
                return startTime > currentTime;
            });
            this.IdData2 = filteredBwics2.map(bwic => bwic.bwicId);
            this.SizeData = filteredBwics2.map(bwic => bwic.size);
            this.StartPriceData = filteredBwics2.map(bwic => bwic.startPrice);

            this.Bar();
            this.linechart();
        });

    }
    Bar() {
        const ec = echarts as any;
        let bar = ec.init(document.getElementById('bar'));
        // let lineChart2 = ec.init(document.getElementById('lineChart2'));
        // let lineChart3 = ec.init(document.getElementById('lineChart3'));
        let barOption = {
            title: {
                text: 'Popular Ongoing BWIC'
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
            // toolbox: {
            //     feature: {
            //         saveAsImage: {}
            //     }
            // },
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
                        color: '#c0c7d9'
                    }
                },
            ]
        };
        bar.setOption(barOption);
        // lineChart2.setOption(barOption);
        // lineChart3.setOption(barOption);
    }
    linechart() {
        const ec = echarts as any;
        let lineChart = ec.init(document.getElementById('lineChart'));
        let lineoption = {
            title: {
                text: 'Uncoming BWIC'
            },
            tooltip: {
                trigger: 'axis'
            },
            legend: {
                data: ['Size', 'StartPrice']
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            // toolbox: {
            //     feature: {
            //         saveAsImage: {}
            //     }
            // },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: this.IdData2,
                name: 'BwicId'
            },
            yAxis: {
                type: 'value'
            },
            series: [
                {
                    name: 'Size',
                    type: 'line',
                    stack: 'Total',
                    data: this.SizeData,
                    itemStyle: {
                        color: '#c0c7d9'
                    }
                },
                {
                    name: 'StartPrice',
                    type: 'line',
                    stack: 'Total',
                    data: this.StartPriceData,
                    itemStyle: {
                        color: '#52dfe9'
                    }
                }
            ]
        };
        lineChart.setOption(lineoption);
    }
}