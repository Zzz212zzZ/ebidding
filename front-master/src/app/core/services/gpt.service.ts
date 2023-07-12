import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, throwError, Observer } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { EventSourcePolyfill } from 'event-source-polyfill';

@Injectable({
  providedIn: 'root'
})
export class GptService {
  constructor(private http: HttpClient) { }

  traderChatWithGpt(message: string): Observable<MessageEvent> {
    const apiUrl = 'api/v1/bwic-service/bwics/chat';
    const authToken = localStorage.getItem('Token') || '';
    const headers = { 'Authorization': authToken };
    let params = new HttpParams();
    params = params.append('message', message);

    return new Observable((observer: Observer<MessageEvent>) => {
      const eventSource = new EventSourcePolyfill(apiUrl + '?' + params.toString(), { headers: headers });

      eventSource.onmessage = ((event: MessageEvent) => {
        console.log('Received data:', event.data);
        observer.next(event);
        
        // 解析数据
        const data = JSON.parse(event.data);
        if (data.choices[0].finish_reason === 'stop') {
          observer.complete();
        }
      }) as any;

      eventSource.onerror = ((error: Event) => observer.error(error)) as  any;

     

      return () => {
        console.log('closing event source');
        eventSource.close();
      };
    }).pipe(catchError(err => {
      console.error('API call failed:', err);
      return throwError(err);
    }));
  }
}
