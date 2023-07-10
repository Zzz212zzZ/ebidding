import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import {ParentItemData } from 'src/app/pages/trader-portal/admin/ongoing-table/ongoing-table.component';
import { BwicUpcomingRecord } from 'src/app/pages/trader-portal/admin/upcoming-table/upcoming-table.component';
import { BwicEndedRecordResponseDTO } from 'src/app/pages/trader-portal/admin/ended-table/ended-table.component';
import { Observable,map } from 'rxjs';


export interface BwicUpcomingFullRecord {
  bwicId: string;
  bondId: string;
  size: number;
  startPrice: number;
  startTime: string;
  dueTime: string;
}




@Injectable({
  providedIn: 'root'
})
export class BwicService {

  constructor(private http: HttpClient) {}


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


//向后端删除upcoming的一个数据
deleteBwicUpcomingRecord(bwicId: string): Observable<any> {
  const apiUrl = `bwic/bwics/${bwicId}`;
  return this.http.delete(apiUrl);
}
//向后端删除upcoming的一个数据




// ---------------向后端请求ended的数据---------------

getEndedBwics(): Observable<BwicEndedRecordResponseDTO[]> {
  const apiUrl = `bwic/bwics/ended`;
  console.log("bwic.service.ts: getEndedBwics(): apiUrl = " + apiUrl);
  return this.http.get<BwicEndedRecordResponseDTO[]>(apiUrl);
}

// ---------------向后端请求ended的数据---------------








  getBidding(value:string){
    return this.http
      .get(`/bwic/bwics/${value}/bycusip`);
  }

  getHistory(){
    return this.http
      .get(`/bwic/bwics/history`).toPromise();
  }
  // http://localhost:8080/api/v1/bid-service/getBidByBwicIdAndAccountId/16
  // "/bwic": {
  //   "target": "http://localhost:8080",
  //   "secure": false,
  //   "changeOrigin": true,
  //   "pathRewrite": {
  //     "^/bwic": "/api/v1/bwic-service"
  //   }
  // },
  // "/bid": {
  //   "target": "http://localhost:8080",
  //   "secure": false,
  //   "changeOrigin": true,
  //   "pathRewrite": {
  //     "^/bid": "/api/v1/bid-service"
  //   }
  getBwicByAccountId(){
    return this.http
    .get(`/bwic/bwics/getBwicByAccountId`).toPromise();
  }

  getBidByBwicIdAndAccountId(id:string){
    return this.http
    .get(`/bid/getBidByBwicIdAndAccountId/`+id).toPromise();
  }
}
