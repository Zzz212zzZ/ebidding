import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {ParentItemData } from 'src/app/pages/trader-portal/admin/ongoing-table/ongoing-table.component';
import { Observable,map } from 'rxjs';

// interface ServerResponseData {
//   bwicId: number;
//   bondId: string;
//   cusip: string;
//   issuer: string;
//   size: number;
//   startTime: string;
//   dueTime: string;
//   lastBidTime: string;
//   bidCounts: number;
//   children: BidRankItemData[];  // here's the change
// }

// interface BidRankItemData {
//   ranking: number;
//   accountId: number;
//   accountName: string;
//   price: number;
//   time: string;
// }


@Injectable({
  providedIn: 'root'
})
export class BwicService {

  constructor(private http: HttpClient) { }


  //---------------向后端请求ongoing的数据----------------
  getOngoingBwics(): Observable<ParentItemData[]> {
    const apiUrl = 'bwic/bwics/ongoing';
    console.log("bwic.service.ts: getOngoingBwics(): apiUrl = " + apiUrl);
    return this.http.get<ParentItemData[]>(apiUrl).pipe(
      map((data: ParentItemData[]) => 
        data.map(item => ({
          ...item,   // 拷贝 item 的所有字段
          expand: false,  // 默认设置为false
          children: item.children.map(child => ({
            ...child,    // 拷贝 child 的所有字段
          }))
        }))
      )
    );
  }
  
  

  //---------------向后端请求ongoing的数据----------------







  getBwics() {
    this.http
      .get('/api/v1/bwic-service/bwics/history')
      .subscribe((data) => {
        console.log(data);
      });
  }

  getBidding(value: string) {
    return this.http
      .get(`/api/bwics/${value}/bycusip`);
  }

  getHistory() {
    return this.http
      .get(`/api/bwics/history`).toPromise();
  }

  setBids(model: { bwicId: string, price: string }) {
    return this.http.post('/bid/bids', model)
  }
}
