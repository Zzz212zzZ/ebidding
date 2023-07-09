import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {ParentItemData } from 'src/app/pages/trader-portal/admin/ongoing-table/ongoing-table.component';
import { BwicUpcomingRecord } from 'src/app/pages/trader-portal/admin/upcoming-table/upcoming-table.component';
import { Observable,map } from 'rxjs';


export interface BwicUpcomingFullRecord {
  bwicId: string;
  bondId: string;
  size: number;
  startPrice: number;
  startTime: Date;
  dueTime: Date;
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



      //---------------向后端请求Upcoming的数据----------------
getUpcomingBwics(): Observable<BwicUpcomingRecord[]> {
  const apiUrl = 'bwic/bwics/upcoming';
  console.log("bwic.service.ts: getUpcomingBwics(): apiUrl = " + apiUrl);
  return this.http.get<BwicUpcomingRecord[]>(apiUrl).pipe(
    map((data: BwicUpcomingRecord[]) => 
      data.map(item => ({
        ...item,   // 拷贝 item 的所有字段
      }))
    )
  );
}
  //---------------向后端请求upComing的数据----------------


//向后端更新upcoming的一个数据

updateBwicUpcomingFullRecord(bwicId: string, record: BwicUpcomingFullRecord): Observable<any> {
  const apiUrl = `bwic/bwics/${bwicId}/full-record`;
  return this.http.put(apiUrl, record);
}
//向后端更新upcoming的一个数据








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
