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
}
