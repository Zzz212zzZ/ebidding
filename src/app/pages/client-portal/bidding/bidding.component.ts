import { Component, OnInit } from '@angular/core';
import { BehaviorSubject, Observable, filter } from 'rxjs';
import { CommonModule } from '@angular/common';
import { NzInputModule } from 'ng-zorro-antd/input';
import { NzIconModule } from 'ng-zorro-antd/icon';
import { NzButtonModule } from 'ng-zorro-antd/button';
import { NzFormModule } from 'ng-zorro-antd/form';
import { NzCardModule } from 'ng-zorro-antd/card';
import { NzTableModule } from 'ng-zorro-antd/table';
import { NzDividerModule } from 'ng-zorro-antd/divider';
import { FormsModule, ReactiveFormsModule, UntypedFormBuilder, UntypedFormGroup } from '@angular/forms';
import { BwicService } from 'src/app/core/services/bwic.service';
import { NzModalModule } from 'ng-zorro-antd/modal';
import { WebsocketBwicService } from 'src/app/core/services/websocket-bwic.service';
import { NzMessageModule, NzMessageService } from 'ng-zorro-antd/message';

interface ItemData {
  bwicId: number;
  issuer: string;
  cusip: string;
  startTime: string;
  dueTime: string;
  startPrice: number;
  size: number;
}

@Component({
  selector: 'app-bidding',
  standalone: true,
  imports: [CommonModule, NzInputModule,
    NzIconModule, NzButtonModule,
    NzFormModule, NzCardModule,
    NzTableModule, NzDividerModule,
    FormsModule,NzModalModule,ReactiveFormsModule ,NzMessageModule],
  templateUrl: './bidding.component.html',
  styleUrls: ['./bidding.component.less']
})



export class BiddingComponent implements OnInit {
  constructor(private bwicService: BwicService,private fb: UntypedFormBuilder,private websocketBwicService:WebsocketBwicService,private message: NzMessageService) {

  }
  isVisible = false;
  searchValue: string = ''
  editCache: { [key: number]: { edit: boolean; data: ItemData } } = {};
  listOfData: ItemData[] = [];

  editData: ItemData | undefined

  validateForm!: UntypedFormGroup;

  price:string = ''

  async submit(){
    const model = {
      bwicId:this.editData!.bwicId + '',
      price:this.price
    }
    await this.bwicService.setBids(model).toPromise()
    this.message.success('Success！')
  }

  startEdit(bwicData: ItemData): void {
    // this.editCache[bwicId].edit = true;
    this.validateForm.reset(bwicData)
    this.validateForm.get('bwicId')?.disable();
    // const socket = this.websocketBwicService.connect(bwicData.bwicId + '');
     // 发送消息
    //  this.websocketBwicService.send('Hello, server!');
    // 接收到消息的处理
    // socket.onmessage = (event) => {
    //   const message = event.data;
    //   console.log('Received message:', message);
    // };
    this.isVisible = true;
    this.editData = bwicData
  }

  cancelEdit(bwicId: number): void {
    const index = this.listOfData.findIndex(item => item.bwicId === bwicId);
    this.editCache[bwicId] = {
      data: { ...this.listOfData[index] },
      edit: false
    };
  }

  saveEdit(bwicId: number): void {
    const index = this.listOfData.findIndex(item => item.bwicId === bwicId);
    Object.assign(this.listOfData[index], this.editCache[bwicId].data);
    this.editCache[bwicId].edit = false;
  }

  updateEditCache(): void {
    this.listOfData.forEach(item => {
      this.editCache[item.bwicId] = {
        edit: false,
        data: { ...item }
      };
    });
  }

  ngOnInit(): void {
    this.getAllData();
    this.validateForm = this.fb.group({
      bwicId: [null],
      issuer: [null],
      cusip: [null],
      startTime: [null],
      dueTime: [null],
      startPrice: [null],
      size: [null],
    });
  }

  searchChange() {
    if (this.searchValue.trim().length === 0) {
      this.getAllData()
    } else {
      this.bwicService.getBidding(this.searchValue).subscribe(res => {
        console.log(res)
        const data: ItemData[] = []
        data.push(res as ItemData)
        this.listOfData = [...data]
      })
    }

  }

  async getAllData() {
    const data = (await this.bwicService.getHistory()) as { active: ItemData[] }
    this.listOfData = [...data!.active]
  }

  showModal(): void {
    this.isVisible = true;
  }

  handleOk(): void {
    console.log('Button ok clicked!');
    this.isVisible = false;
  }

  handleCancel(): void {
    console.log('Button cancel clicked!');
    // 关闭moadl同时关闭 WebSocket 连接
    // this.websocketBwicService.disconnect();
    this.isVisible = false;
  }
}