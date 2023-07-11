import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BidRankItemData } from 'src/app/pages/trader-portal/admin/ongoing-table/ongoing-table.component';

@Injectable({
  providedIn: 'root'
})
export class BidService {

  constructor(private http: HttpClient) {}

  setBids(model:{bwicId:string,price:string}){
    return this.http.post('/bid/bids',model)
  }


  getAllBidRankingsByBwicId(bwicId: number): Observable<BidRankItemData[]> {
    return this.http.get<BidRankItemData[]>(`bid/bwics/${bwicId}/ongoing-all-items`);
  }
}
