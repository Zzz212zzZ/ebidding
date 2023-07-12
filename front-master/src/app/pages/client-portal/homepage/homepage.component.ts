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
        MatDividerModule,MatIconModule,
        MatProgressBarModule,MatInputModule],
    templateUrl: './homepage.component.html',
    styleUrls: ['./homepage.component.less']
})


export class HomepageComponent implements OnInit {
    AllBwics: Bwics[] = [];
    IdData: number[] = [];
    CountsData: number[] = [];
    constructor(private bwicService: BwicService) {}

    ngOnInit() {
        this.bwicService.getAllBwics().subscribe((data: Bwics[]) => {
            this.AllBwics = data;
            let sortedBwics = [...this.AllBwics]; // 创建副本用于排序
            sortedBwics.sort((a, b) => b.bidCounts - a.bidCounts);
            //第一个表
            for (let i = 5; i > 0; i--) {
                this.CountsData.push(sortedBwics[i].bidCounts);
                this.IdData.push(sortedBwics[i].bwicId);
            }
            this.Bar();
        });

    }
    Bar() {
        const ec = echarts as any;
        let lineChart = ec.init(document.getElementById('lineChart'));
        // let lineChart2 = ec.init(document.getElementById('lineChart2'));
        // let lineChart3 = ec.init(document.getElementById('lineChart3'));
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
                data: this.IdData,
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
        lineChart.setOption(barOption);
        // lineChart2.setOption(barOption);
        // lineChart3.setOption(barOption);
    }
}