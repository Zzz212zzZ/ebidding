import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class BwicService {
  
  constructor(private http: HttpClient) {}


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
