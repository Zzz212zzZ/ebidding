import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {ParentItemData } from 'src/app/pages/trader-portal/admin/ongoing-table/ongoing-table.component';
import { Observable,map } from 'rxjs';

interface ServerResponseData {
  bwicId: number;
  bondId: string;
  cusip: string;
  issuer: string;
  size: number;
  startTime: string;
  dueTime: string;
  lastBidTime: string;
  bidCounts: number;
}


@Injectable({
  providedIn: 'root'
})
export class BwicService {

  constructor(private http: HttpClient) { }


  //---------------向后端请求ongoing的数据----------------
   getOngoingBwics(): Observable<ParentItemData[]> {
    const apiUrl = 'bwic/bwics/ongoing';
    console.log("bwic.service.ts: getOngoingBwics(): apiUrl = " + apiUrl);
    return this.http.get<ServerResponseData[]>(apiUrl).pipe(
      map((data: ServerResponseData[]) => 
        data.map(item => ({
          bwic_id: item.bwicId,
          bond_id: item.bondId,
          cusip: item.cusip,
          issuer: item.issuer,
          size: item.size,
          start_time: item.startTime,
          due_time: item.dueTime,
          last_bid_time: item.lastBidTime,
          bid_counts: item.bidCounts,
          expand: false,  // 默认设置为false
          children: []
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
