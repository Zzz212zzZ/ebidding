import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class BwicService {
  
  constructor(private http: HttpClient) {}

  getBwics() {
    this.http
      .get('/api/v1/bwic-service/bwics/history')
      .subscribe((data) => {
        console.log(data);
      });
  }

  getBidding(value:string){
    return this.http
      .get(`/api/bwics/${value}/bycusip`);
  }

  getHistory(){
    return this.http
      .get(`/api/bwics/history`).toPromise();
  }

  setBids(model:{bwicId:string,price:string}){
    return this.http.post('/bid/bids',model)
  }
}
