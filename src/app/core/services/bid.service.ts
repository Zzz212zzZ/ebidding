import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class BidService {

  constructor(private http: HttpClient) {}

  setBids(model:{bwicId:string,price:string}){
    return this.http.post('/bid/bids',model)
  }
}
